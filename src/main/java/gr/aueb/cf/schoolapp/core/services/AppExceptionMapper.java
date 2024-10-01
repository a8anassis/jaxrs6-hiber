package gr.aueb.cf.schoolapp.core.services;

import gr.aueb.cf.schoolapp.core.exceptions.*;
import gr.aueb.cf.schoolapp.dto.ResponseMessageDTO;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AppExceptionMapper implements ExceptionMapper<EntityGenericException> {

    @Override
    public Response toResponse(EntityGenericException exception) {

        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;

        if (exception instanceof EntityNotFoundException) {
            status = Response.Status.NOT_FOUND;
        } else if (exception instanceof EntityInvalidArgumentException) {
            status = Response.Status.BAD_REQUEST;
        } else if (exception instanceof EntityNotAuthorizedException) {
            status = Response.Status.UNAUTHORIZED;
        } else if (exception instanceof EntityAlreadyExistsException) {
            status = Response.Status.CONFLICT;
        } else if (exception instanceof AppServerException) {
            status = Response.Status.SERVICE_UNAVAILABLE;
        }

        return Response
                .status(status)
                .entity(new ResponseMessageDTO(exception.getCode(), exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
