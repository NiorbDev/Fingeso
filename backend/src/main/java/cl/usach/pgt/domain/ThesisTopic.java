package cl.usach.pgt.domain;

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
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "thesis_topics")
public class ThesisTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 900)
    private String summary;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private String area;

    @Column(nullable = false)
    private String professor;

    @Column(nullable = false)
    private String professorEmail;

    @Column(nullable = false)
    private String professorDepartment;

    @Column(nullable = false)
    private int totalSlots;

    @Column(nullable = false)
    private int reservedSlots;

    @Column(nullable = false)
    private String modality;

    @Column(nullable = false)
    private String dedication;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TopicStatus status;

    @Column(nullable = false)
    private boolean featured;

    @Column(nullable = false)
    private LocalDate publishedAt;

    @Version
    private long version;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "topic_technologies", joinColumns = @JoinColumn(name = "topic_id"))
    @Column(name = "technology", nullable = false)
    private List<String> technologies = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "topic_objectives", joinColumns = @JoinColumn(name = "topic_id"))
    @Column(name = "objective", nullable = false, length = 500)
    private List<String> objectives = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "topic_requirements", joinColumns = @JoinColumn(name = "topic_id"))
    @Column(name = "requirement", nullable = false, length = 500)
    private List<String> requirements = new ArrayList<>();

    protected ThesisTopic() {}

    public ThesisTopic(String code, String title, String summary, String description, String area,
            String professor, String professorEmail, String professorDepartment, int totalSlots,
            int reservedSlots, String modality, String dedication, String location, boolean featured,
            LocalDate publishedAt, List<String> technologies, List<String> objectives, List<String> requirements) {
        this.code = code;
        this.title = title;
        this.summary = summary;
        this.description = description;
        this.area = area;
        this.professor = professor;
        this.professorEmail = professorEmail;
        this.professorDepartment = professorDepartment;
        this.totalSlots = totalSlots;
        this.reservedSlots = reservedSlots;
        this.modality = modality;
        this.dedication = dedication;
        this.location = location;
        this.status = TopicStatus.AVAILABLE;
        this.featured = featured;
        this.publishedAt = publishedAt;
        this.technologies = new ArrayList<>(technologies);
        this.objectives = new ArrayList<>(objectives);
        this.requirements = new ArrayList<>(requirements);
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public String getSummary() { return summary; }
    public String getDescription() { return description; }
    public String getArea() { return area; }
    public String getProfessor() { return professor; }
    public String getProfessorEmail() { return professorEmail; }
    public String getProfessorDepartment() { return professorDepartment; }
    public int getTotalSlots() { return totalSlots; }
    public int getReservedSlots() { return reservedSlots; }
    public String getModality() { return modality; }
    public String getDedication() { return dedication; }
    public String getLocation() { return location; }
    public TopicStatus getStatus() { return status; }
    public boolean isFeatured() { return featured; }
    public LocalDate getPublishedAt() { return publishedAt; }
    public List<String> getTechnologies() { return List.copyOf(technologies); }
    public List<String> getObjectives() { return List.copyOf(objectives); }
    public List<String> getRequirements() { return List.copyOf(requirements); }
    public int getAvailableSlots() { return totalSlots - reservedSlots; }

    public void reserveSlots(int amount) {
        if (amount < 1 || amount > getAvailableSlots()) {
            throw new IllegalArgumentException("No existen cupos suficientes para esta postulacion.");
        }
        this.reservedSlots += amount;
        if (reservedSlots >= totalSlots) this.status = TopicStatus.RESERVED;
    }
}
