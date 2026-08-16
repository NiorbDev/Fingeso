package pgt.repository;

import pgt.domain.AuditEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEntryRepository extends JpaRepository<AuditEntry, Long> {}

