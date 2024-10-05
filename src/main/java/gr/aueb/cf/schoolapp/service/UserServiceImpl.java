package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.core.exceptions.AppServerException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.UserInsertDTO;
import gr.aueb.cf.schoolapp.dto.UserReadOnlyDTO;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.util.JPAHelper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Inheritance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
//@Slf4j
public class UserServiceImpl implements IUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final IUserDAO userDAO;

    @Override
    public UserReadOnlyDTO insertUser(UserInsertDTO dto)
            throws AppServerException {
        try {
            JPAHelper.beginTransaction();
            User user = Mapper.mapToUser(dto);
//            if (userDAO.getByUsername(dto.getUsername()).isPresent()) {
//                throw new EntityAlreadyExistsException("User", "User with username: " + dto.getUsername()
//                + " already exists");
//            }
            UserReadOnlyDTO readOnlyDTO = userDAO.insert(user)
                    .map(Mapper::mapToUserReadOnlyDTO)
                    .orElseThrow(() -> new AppServerException("User", "User with vat: " + dto.getUsername() +
                            " not inserted"));
            JPAHelper.commitTransaction();
            LOGGER.info("User with username: {} inserted", dto.getUsername());
            return readOnlyDTO;
        } catch (AppServerException e) {
            JPAHelper.rollbackTransaction();
            LOGGER.error("Error. User with username: {} not inserted", dto.getUsername());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public UserReadOnlyDTO getUserByUsername(String username) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();

            UserReadOnlyDTO userReadOnlyDTO = userDAO.getByUsername(username)
                    .map(Mapper::mapToUserReadOnlyDTO)
                    .orElseThrow(() -> new EntityNotFoundException("User", "User with username: " +
                            username + " not found"));
            JPAHelper.commitTransaction();
            return userReadOnlyDTO;
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Warning. User with username {} not found", username);
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public boolean isUserValid(String username, String password) {
        try {
            JPAHelper.beginTransaction();
            boolean isValid = userDAO.isUserValid(username, password);
            JPAHelper.commitTransaction();
            return isValid;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public boolean isEmailExists(String username) {
        try {
            JPAHelper.beginTransaction();
            boolean mailExists = userDAO.isEmailExists(username);
            JPAHelper.commitTransaction();
            return mailExists;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }
}
