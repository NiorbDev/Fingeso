package cl.usach.pgt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ApplicationMember {
    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(name = "member_email", nullable = false)
    private String email;

    protected ApplicationMember() {}

    public ApplicationMember(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
}

