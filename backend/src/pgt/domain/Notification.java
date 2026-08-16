package pgt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String recipientEmail;

    @Column(nullable = false, length = 800)
    private String message;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private boolean read;

    protected Notification() {}

    public Notification(String recipientEmail, String message) {
        this.recipientEmail = recipientEmail;
        this.message = message;
        this.createdAt = OffsetDateTime.now();
        this.read = false;
    }
}

