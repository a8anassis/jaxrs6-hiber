package gr.aueb.cf.schoolapp.service.exceptions;

import gr.aueb.cf.schoolapp.model.IdentifiableEntity;

import java.io.Serial;

public class EntityNotFoundException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(Class<? extends IdentifiableEntity> enityClass, Long id) {
        super("Entity " + enityClass.getSimpleName() + " with id " + id + " not found");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
