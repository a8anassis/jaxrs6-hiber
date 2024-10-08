package gr.aueb.cf.schoolapp.rest;

import gr.aueb.cf.schoolapp.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.dto.TeacherFiltersDTO;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.validator.ValidatorUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

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
    public Response addTeacher(TeacherInsertDTO insertDTO, @Context UriInfo uriInfo)
    throws EntityInvalidArgumentException, EntityAlreadyExistsException {
        List<String> errors = ValidatorUtil.validateDTO(insertDTO);
        if (!errors.isEmpty()) {
            throw new EntityInvalidArgumentException("Teacher", String.join("\n", errors));
        }
        TeacherReadOnlyDTO readOnlyDTO = teacherService.insertTeacher(insertDTO);
        return Response.created(uriInfo.getAbsolutePathBuilder().path(readOnlyDTO.getId().toString()).build())
                .entity(readOnlyDTO)
                .build();
    }

    @PUT
    @Path("/{teacherId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTeacher(@PathParam("teacherId") Long teacherId,
                                  TeacherUpdateDTO updateDTO)
            throws EntityInvalidArgumentException, EntityNotFoundException {
        List<String> errors = ValidatorUtil.validateDTO(updateDTO);
        if (!errors.isEmpty()) {
            throw new EntityInvalidArgumentException("Teacher", String.join(", ", errors));
        }

        TeacherReadOnlyDTO readOnlyDTO = teacherService.updateTeacher(updateDTO);
        return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
    }

    @DELETE
    @Path("/{teacherId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTeacher(@PathParam("teacherId") Long teacherId)
            throws EntityNotFoundException {

        TeacherReadOnlyDTO dto = teacherService.getTeacherById(teacherId);
        teacherService.deleteTeacher(teacherId);
        return Response.status(Response.Status.OK).entity(dto).build();
    }

    @GET
    @Path("/{teacherId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacher(@PathParam("teacherId") Long id,
                               @Context SecurityContext securityContext)
            throws EntityNotFoundException {

        if (!securityContext.isUserInRole("TEACHER")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        TeacherReadOnlyDTO dto = teacherService.getTeacherById(id);
        return Response.status(Response.Status.OK).entity(dto).build();
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFiltered(@QueryParam("firstname") String firstname,
                                @QueryParam("lastname") String lastname,
                                @QueryParam("vat") String vat) {

        TeacherFiltersDTO filtersDTO = new TeacherFiltersDTO(firstname, lastname, vat);
        Map<String, Object> criteria;
        criteria = Mapper.mapToCriteria(filtersDTO);
        List<TeacherReadOnlyDTO> readOnlyDTOS = teacherService.getTeachersByCriteria(criteria);
        return Response.status(Response.Status.OK).entity(readOnlyDTOS).build();
    }


}
