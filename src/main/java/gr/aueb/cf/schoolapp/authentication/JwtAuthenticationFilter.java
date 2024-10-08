package gr.aueb.cf.schoolapp.authentication;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.security.CustomSecurityContext;
import gr.aueb.cf.schoolapp.security.JwtService;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URI;

@Provider
@Priority(Priorities.AUTHENTICATION)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class JwtAuthenticationFilter implements ContainerRequestFilter {

    private final JwtService jwtService;
    private final IUserDAO userDAO;

    @Context
    SecurityContext securityContext;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        UriInfo uriInfo = requestContext.getUriInfo();

        // if we have /api/auth/register
        // getPath() will return auth/register
        String path = uriInfo.getPath();
        if (isPublicPath(path)) {
            return;
        }

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String token = authorizationHeader.substring("Bearer ".length()).trim();

        try {
            String username = jwtService.extractSubject(token);
            if (username != null &&
                    securityContext == null || securityContext.getUserPrincipal() == null) {
                User user = userDAO.getByUsername(username).orElse(null);
                if (user != null && jwtService.isTokenValid(token, user)) {
                    requestContext.setSecurityContext(new CustomSecurityContext(user));
                } else {
                    System.out.println("Token is not valid" + requestContext.getUriInfo());
                    //
                }
            }
        } catch (Exception e) {
            throw new NotAuthorizedException("Invalid token");
        }
    }

    private boolean isPublicPath(String path) {
        return path.equals("auth/register") || path.equals("auth/login");
    }
}
