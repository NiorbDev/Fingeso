package cl.usach.pgt.dto;

import cl.usach.pgt.domain.UserAccount;

public record AuthResponse(
        Long id,
        String name,
        String firstName,
        String email,
        String role,
        String initials,
        String career,
        int progress) {

    public static AuthResponse from(UserAccount user) {
        String[] names = user.getName().trim().split("\\s+");
        String firstName = names[0];
        String initials = names.length > 1
                ? (names[0].substring(0, 1) + names[1].substring(0, 1)).toUpperCase()
                : names[0].substring(0, Math.min(2, names[0].length())).toUpperCase();
        String role = switch (user.getRole()) {
            case STUDENT -> "Estudiante";
            case PROFESSOR -> "Profesor";
            case COORDINATOR -> "Coordinador";
        };
        return new AuthResponse(user.getId(), user.getName(), firstName, user.getEmail(), role, initials,
                user.getCareer(), 72);
    }
}

