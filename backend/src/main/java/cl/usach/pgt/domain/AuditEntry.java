package cl.usach.pgt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "audit_entries")
public class AuditEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String entityType;

    @Column(nullable = false)
    private Long entityId;

    @Column(nullable = false)
    private String event;

    @Column(nullable = false)
    private String actorEmail;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    protected AuditEntry() {}

    public AuditEntry(String entityType, Long entityId, String event, String actorEmail) {
        this.entityType = entityType;
        this.entityId = entityId;
        this.event = event;
        this.actorEmail = actorEmail;
        this.createdAt = OffsetDateTime.now();
    }
}

