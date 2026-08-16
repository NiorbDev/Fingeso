package pgt.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "thesis_applications")
public class ThesisApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private UserAccount student;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private ThesisTopic topic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationModality modality;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationState state;

    @Column(nullable = false, length = 600)
    private String message;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "application_members", joinColumns = @JoinColumn(name = "application_id"))
    private List<ApplicationMember> members = new ArrayList<>();

    protected ThesisApplication() {}

    public ThesisApplication(UserAccount student, ThesisTopic topic, ApplicationModality modality,
            String message, List<ApplicationMember> members) {
        this.student = student;
        this.topic = topic;
        this.modality = modality;
        this.message = message;
        this.members = new ArrayList<>(members);
        this.state = ApplicationState.PENDING;
        this.createdAt = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public UserAccount getStudent() { return student; }
    public ThesisTopic getTopic() { return topic; }
    public ApplicationModality getModality() { return modality; }
    public ApplicationState getState() { return state; }
    public String getMessage() { return message; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public List<ApplicationMember> getMembers() { return List.copyOf(members); }
}

