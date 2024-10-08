package gr.aueb.cf.schoolapp.rest;

import gr.aueb.cf.schoolapp.authentication.AuthenticationProvider;
import gr.aueb.cf.schoolapp.authentication.AuthenticationResponseDTO;
import gr.aueb.cf.schoolapp.core.exceptions.AppServerException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.dto.UserInsertDTO;
import gr.aueb.cf.schoolapp.dto.UserLoginDTO;
import gr.aueb.cf.schoolapp.dto.UserReadOnlyDTO;
import gr.aueb.cf.schoolapp.security.JwtService;
import gr.aueb.cf.schoolapp.service.IUserService;
import gr.aueb.cf.schoolapp.validator.UserInputValidator;
import gr.aueb.cf.schoolapp.validator.ValidatorUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Path("/auth")
public class AuthRestController {

    private final IUserService userService;
    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(UserInsertDTO userInsertDTO, @Context UriInfo uriInfo)
    throws EntityInvalidArgumentException, AppServerException {
        UserReadOnlyDTO userReadOnlyDTO;

        List<String> beanErrors = ValidatorUtil.validateDTO(userInsertDTO);
        if (!beanErrors.isEmpty()) {
            throw new EntityInvalidArgumentException("User", String.join(", ", beanErrors));
        }

        Map<String , String > otherErrors = UserInputValidator.validate(userInsertDTO);
        if (!otherErrors.isEmpty()) {
            throw new EntityInvalidArgumentException("User", otherErrors.toString());
        }

        userReadOnlyDTO = userService.insertUser(userInsertDTO);
        return Response.created(uriInfo.getAbsolutePathBuilder().path(userReadOnlyDTO.getId().toString()).build())
                .entity(userReadOnlyDTO).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserLoginDTO loginDTO, @Context Principal principal)
            throws EntityNotFoundException {
        boolean isUserValid = authenticationProvider.authenticate(loginDTO);
        if (!isUserValid) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if (principal != null) {
            String username = principal.getName();
            if (loginDTO.getUsername().equals(username)) {
                return Response.status(Response.Status.OK).entity("Already authenticated").build();
            }
        }

        UserReadOnlyDTO userReadOnlyDTO = userService.getUserByUsername(loginDTO.getUsername());
        String role = userReadOnlyDTO.getRole();
        String token = jwtService.generateToken(loginDTO.getUsername(), role);
        AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO(token);
        return Response.status(Response.Status.OK).entity(authenticationResponseDTO).build();
    }
}
