package cl.usach.pgt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cl.usach.pgt.domain.ApplicationModality;
import cl.usach.pgt.dto.ApplicationRequest;
import cl.usach.pgt.dto.ApplicationResponse;
import cl.usach.pgt.dto.AuthRequest;
import cl.usach.pgt.dto.MemberRequest;
import cl.usach.pgt.exception.BusinessRuleException;
import cl.usach.pgt.repository.NotificationRepository;
import cl.usach.pgt.repository.ThesisTopicRepository;
import cl.usach.pgt.repository.UserAccountRepository;
import cl.usach.pgt.service.ApplicationService;
import cl.usach.pgt.service.AuthService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ApplicationFlowIntegrationTest {
    @Autowired ApplicationService applicationService;
    @Autowired AuthService authService;
    @Autowired UserAccountRepository users;
    @Autowired ThesisTopicRepository topics;
    @Autowired NotificationRepository notifications;

    @Test
    void authenticatesInstitutionalDemoUser() {
        var user = authService.login(new AuthRequest("estudiante@usach.cl", "usach2026"));
        assertThat(user.role()).isEqualTo("Estudiante");
        assertThat(user.firstName()).isEqualTo("Martina");
    }

    @Test
    void registersApplicationReservesSlotAndNotifiesProfessor() {
        var student = users.findByEmailIgnoreCase("estudiante@usach.cl").orElseThrow();
        var topic = topics.findByStatusInOrderByPublishedAtDesc(List.of(cl.usach.pgt.domain.TopicStatus.AVAILABLE))
                .stream().filter(candidate -> candidate.getAvailableSlots() >= 1).findFirst().orElseThrow();
        int slotsBefore = topic.getAvailableSlots();
        long notificationsBefore = notifications.count();

        ApplicationResponse response = applicationService.create(new ApplicationRequest(
                student.getId(), topic.getId(), ApplicationModality.INDIVIDUAL,
                "Me interesa aportar experiencia en desarrollo y aprender del problema propuesto.",
                List.of(new MemberRequest(student.getName(), student.getEmail()))));

        assertThat(response.state()).isEqualTo("PENDIENTE");
        assertThat(response.topic().id()).isEqualTo(topic.getId());
        assertThat(topics.findById(topic.getId()).orElseThrow().getAvailableSlots()).isEqualTo(slotsBefore - 1);
        assertThat(notifications.count()).isEqualTo(notificationsBefore + 1);
    }

    @Test
    void rejectsGroupApplicationWithoutEnoughMembers() {
        var student = users.findByEmailIgnoreCase("estudiante@usach.cl").orElseThrow();
        var topic = topics.findByStatusInOrderByPublishedAtDesc(List.of(cl.usach.pgt.domain.TopicStatus.AVAILABLE)).getFirst();

        assertThatThrownBy(() -> applicationService.create(new ApplicationRequest(
                student.getId(), topic.getId(), ApplicationModality.GROUP,
                "Quiero postular en modalidad grupal para desarrollar esta propuesta académica.",
                List.of(new MemberRequest(student.getName(), student.getEmail())))))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("al menos dos integrantes");
    }
}

