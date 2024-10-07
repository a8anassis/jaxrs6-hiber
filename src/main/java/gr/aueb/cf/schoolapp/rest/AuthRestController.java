package gr.aueb.cf.schoolapp.rest;

import gr.aueb.cf.schoolapp.authentication.AuthenticationProvider;
import gr.aueb.cf.schoolapp.core.exceptions.AppServerException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolapp.dto.UserInsertDTO;
import gr.aueb.cf.schoolapp.dto.UserReadOnlyDTO;
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

import java.util.List;
import java.util.Map;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Path("/auth")
public class AuthRestController {

    private final IUserService userService;
    private final AuthenticationProvider authenticationProvider;
    // todo JwtService

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


}
