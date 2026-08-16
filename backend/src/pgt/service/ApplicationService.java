package pgt.service;

import pgt.domain.ApplicationMember;
import pgt.domain.ApplicationModality;
import pgt.domain.ApplicationState;
import pgt.domain.AuditEntry;
import pgt.domain.Notification;
import pgt.domain.ThesisApplication;
import pgt.domain.ThesisTopic;
import pgt.domain.TopicStatus;
import pgt.domain.UserAccount;
import pgt.domain.UserRole;
import pgt.dto.ApplicationRequest;
import pgt.dto.ApplicationResponse;
import pgt.dto.MemberRequest;
import pgt.repository.AuditEntryRepository;
import pgt.repository.NotificationRepository;
import pgt.repository.ThesisApplicationRepository;
import pgt.repository.ThesisTopicRepository;
import pgt.repository.UserAccountRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationService {
    private final ThesisApplicationRepository applications;
    private final ThesisTopicRepository topics;
    private final UserAccountRepository users;
    private final NotificationRepository notifications;
    private final AuditEntryRepository auditEntries;

    public ApplicationService(ThesisApplicationRepository applications, ThesisTopicRepository topics,
            UserAccountRepository users, NotificationRepository notifications, AuditEntryRepository auditEntries) {
        this.applications = applications;
        this.topics = topics;
        this.users = users;
        this.notifications = notifications;
        this.auditEntries = auditEntries;
    }

    @Transactional
    public ApplicationResponse create(ApplicationRequest request) {
        UserAccount student = users.findById(request.studentId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró al estudiante."));
        if (student.getRole() != UserRole.STUDENT) {
            throw new IllegalArgumentException("Solo un estudiante puede postular a un tema de tesis.");
        }
        if (student.hasActiveThesis()) {
            throw new IllegalArgumentException("Ya no puede optar a las postulaciones porque tiene una tesis asignada.");
        }

        ThesisTopic topic = topics.findByIdForUpdate(request.topicId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el tema seleccionado."));
        if (topic.getStatus() != TopicStatus.AVAILABLE || topic.getAvailableSlots() < request.members().size()) {
            throw new IllegalArgumentException("El tema ya no tiene cupos suficientes para esta postulación.");
        }
        if (applications.existsByStudentIdAndTopicIdAndState(student.getId(), topic.getId(), ApplicationState.PENDING)) {
            throw new IllegalArgumentException("Ya tienes una postulación pendiente para este tema.");
        }

        validateComposition(request, student);
        List<ApplicationMember> members = request.members().stream()
                .map(member -> new ApplicationMember(member.name().trim(), member.email().trim().toLowerCase()))
                .toList();

        topic.reserveSlots(members.size());
        ThesisApplication application = applications.save(new ThesisApplication(
                student, topic, request.modality(), request.message().trim(), members));
        notifications.save(new Notification(topic.getProfessorEmail(),
                student.getName() + " ha postulado al tema " + topic.getTitle() + "."));
        auditEntries.save(new AuditEntry("ThesisApplication", application.getId(),
                "POSTULACION_REGISTRADA", student.getEmail()));
        return ApplicationResponse.from(application);
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> listForStudent(Long studentId) {
        if (!users.existsById(studentId)) throw new IllegalArgumentException("No se encontró al estudiante.");
        return applications.findByStudentIdOrderByCreatedAtDesc(studentId)
                .stream().map(ApplicationResponse::from).toList();
    }

    private void validateComposition(ApplicationRequest request, UserAccount student) {
        int size = request.members().size();
        if (request.modality() == ApplicationModality.INDIVIDUAL && size != 1) {
            throw new IllegalArgumentException("Una postulación individual debe incluir solamente al estudiante actual.");
        }
        if (request.modality() == ApplicationModality.GROUP && size < 2) {
            throw new IllegalArgumentException("Una postulación grupal debe incluir al menos dos integrantes.");
        }
        if (!request.members().getFirst().email().equalsIgnoreCase(student.getEmail())) {
            throw new IllegalArgumentException("El primer integrante debe corresponder al estudiante que inició sesión.");
        }
        for (MemberRequest member : request.members()) {
            if (!member.email().trim().toLowerCase().endsWith("@usach.cl")) {
                throw new IllegalArgumentException("Todos los integrantes deben usar un correo institucional @usach.cl.");
            }
        }
        long uniqueEmails = request.members().stream().map(member -> member.email().trim().toLowerCase()).distinct().count();
        if (uniqueEmails != size) throw new IllegalArgumentException("No puedes repetir integrantes en la postulación.");
    }
}
