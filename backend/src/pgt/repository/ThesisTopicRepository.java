package pgt.repository;

import pgt.domain.ThesisTopic;
import pgt.domain.TopicStatus;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ThesisTopicRepository extends JpaRepository<ThesisTopic, Long> {
    List<ThesisTopic> findByStatusInOrderByPublishedAtDesc(Collection<TopicStatus> statuses);
    boolean existsByCode(String code);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select t from ThesisTopic t where t.id = :id")
    Optional<ThesisTopic> findByIdForUpdate(@Param("id") Long id);
}
