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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UserServiceImpl implements IUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final IUserDAO userDAO;

    @Override
    public UserReadOnlyDTO insertUser(UserInsertDTO dto)
            throws EntityAlreadyExistsException, AppServerException {
        return null;
//        try {
//            JPAHelper.beginTransaction();
//            User user = Mapper.mapToUser(dto);
//            userDAO.getByUsername(dto.getUsername()).orElseThrow(() ->
//                    new EntityAlreadyExistsException("Teacher", "Teacher with vat: "
//                            + insertDTO.getVat() + " already exists"));
//
//            TeacherReadOnlyDTO readOnlyDTO = teacherDAO.insert(teacher)
//                    .map(Mapper::mapToTeacherReadOnlyDTO)
//                    .orElseThrow(() -> new EntityInvalidArgumentException("Teacher", "Teacher with vat: " +
//                            insertDTO.getVat() + " not inserted"));
//
//            JPAHelper.commitTransaction();
//        } catch (EntityAlreadyExistsException | AppServerException e) {
//
//        }
    }

    @Override
    public UserReadOnlyDTO getUserByUsername(String username) throws EntityNotFoundException {
        return null;
    }

    @Override
    public boolean isUserValid(String username, String password) {
        return false;
    }

    @Override
    public boolean isEmailExists(String username) {
        return false;
    }
}
