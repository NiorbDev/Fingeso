package cl.usach.pgt.repository;

import cl.usach.pgt.domain.ApplicationState;
import cl.usach.pgt.domain.ThesisApplication;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThesisApplicationRepository extends JpaRepository<ThesisApplication, Long> {
    List<ThesisApplication> findByStudentIdOrderByCreatedAtDesc(Long studentId);
    boolean existsByStudentIdAndStateIn(Long studentId, Collection<ApplicationState> states);
    boolean existsByStudentIdAndTopicIdAndState(Long studentId, Long topicId, ApplicationState state);
}

