package cl.usach.pgt.service;

import cl.usach.pgt.domain.UserAccount;
import cl.usach.pgt.dto.AuthRequest;
import cl.usach.pgt.dto.AuthResponse;
import cl.usach.pgt.exception.BusinessRuleException;
import cl.usach.pgt.repository.UserAccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserAccountRepository users;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserAccountRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(AuthRequest request) {
        String email = request.email().trim().toLowerCase();
        if (!email.endsWith("@usach.cl")) {
            throw new BusinessRuleException("El acceso está restringido a correos institucionales registrados.");
        }
        UserAccount user = users.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new BusinessRuleException("El correo o la clave no son correctos."));
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BusinessRuleException("El correo o la clave no son correctos.");
        }
        return AuthResponse.from(user);
    }
}

