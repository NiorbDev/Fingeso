package cl.usach.pgt.dto;

import java.time.OffsetDateTime;
import java.util.Map;

public record ErrorResponse(
        OffsetDateTime timestamp,
        int status,
        String message,
        Map<String, String> errors) {}

