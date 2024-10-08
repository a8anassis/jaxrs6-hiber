package gr.aueb.cf.schoolapp.security;

import gr.aueb.cf.schoolapp.model.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.SecurityContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.security.Principal;

@RequestScoped
@NoArgsConstructor
@AllArgsConstructor
public class CustomSecurityContext implements SecurityContext {

    private User user;

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String role) {
        return user.getRoleType().name().equals(role);
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return "Bearer";
    }
}
