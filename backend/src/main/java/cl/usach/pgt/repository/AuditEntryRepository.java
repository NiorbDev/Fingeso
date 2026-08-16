package cl.usach.pgt.repository;

import cl.usach.pgt.domain.AuditEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEntryRepository extends JpaRepository<AuditEntry, Long> {}

