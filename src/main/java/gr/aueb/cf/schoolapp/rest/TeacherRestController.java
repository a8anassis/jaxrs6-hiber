package gr.aueb.cf.schoolapp.rest;

import com.google.protobuf.MapEntry;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.validator.ValidatorUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
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

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Path("/teachers")
public class TeacherRestController {

    //@Inject
    private final ITeacherService teacherService;

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTeacher(TeacherInsertDTO insertDTO, @Context UriInfo uriInfo) {
        List<String> errors = ValidatorUtil.validateDTO(insertDTO);
        if (!errors.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }

        try {
            TeacherReadOnlyDTO readOnlyDTO = teacherService.insertTeacher(insertDTO);

            return Response.created(uriInfo.getAbsolutePathBuilder().path(readOnlyDTO.getId().toString()).build())
                    .entity(readOnlyDTO).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
