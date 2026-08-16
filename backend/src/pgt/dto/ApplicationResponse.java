package pgt.dto;

import pgt.domain.ApplicationMember;
import pgt.domain.ThesisApplication;
import java.time.OffsetDateTime;
import java.util.List;

public record ApplicationResponse(
        Long id,
        String state,
        OffsetDateTime createdAt,
        String modality,
        String message,
        List<MemberResponse> members,
        TopicResponse topic) {

    public record MemberResponse(String name, String email) {
        static MemberResponse from(ApplicationMember member) {
            return new MemberResponse(member.getName(), member.getEmail());
        }
    }

    public static ApplicationResponse from(ThesisApplication application) {
        String state = switch (application.getState()) {
            case PENDING -> "PENDIENTE";
            case ACCEPTED -> "ACEPTADA";
            case REJECTED -> "RECHAZADA";
        };
        return new ApplicationResponse(application.getId(), state, application.getCreatedAt(),
                application.getModality().name(), application.getMessage(),
                application.getMembers().stream().map(MemberResponse::from).toList(),
                TopicResponse.from(application.getTopic()));
    }
}

