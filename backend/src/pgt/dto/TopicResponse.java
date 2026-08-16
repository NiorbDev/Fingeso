package pgt.dto;

import pgt.domain.ThesisTopic;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public record TopicResponse(
        Long id,
        String code,
        String title,
        String summary,
        String description,
        String area,
        String professor,
        String professorEmail,
        String professorDepartment,
        int totalSlots,
        int reservedSlots,
        String modality,
        String dedication,
        String location,
        List<String> technologies,
        List<String> objectives,
        List<String> requirements,
        boolean featured,
        String publishedAt) {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd MMM uuuu", new Locale("es", "CL"));

    public static TopicResponse from(ThesisTopic topic) {
        return new TopicResponse(topic.getId(), topic.getCode(), topic.getTitle(), topic.getSummary(),
                topic.getDescription(), topic.getArea(), topic.getProfessor(), topic.getProfessorEmail(),
                topic.getProfessorDepartment(), topic.getTotalSlots(), topic.getReservedSlots(),
                topic.getModality(), topic.getDedication(), topic.getLocation(), topic.getTechnologies(),
                topic.getObjectives(), topic.getRequirements(), topic.isFeatured(),
                topic.getPublishedAt().format(DATE_FORMAT).toUpperCase(new Locale("es", "CL")));
    }
}

