package cl.usach.pgt.dto;

import cl.usach.pgt.domain.ApplicationModality;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record ApplicationRequest(
        @NotNull Long studentId,
        @NotNull Long topicId,
        @NotNull ApplicationModality modality,
        @Size(min = 20, max = 600) String message,
        @NotEmpty List<@Valid MemberRequest> members) {}

