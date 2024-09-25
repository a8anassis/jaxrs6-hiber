package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.service.util.JPAHelper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ext.Provider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Provider
@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TeacherServiceImpl implements ITeacherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);

    //@Inject
    private final ITeacherDAO teacherDAO;

    @Override
    public TeacherReadOnlyDTO insertTeacher(TeacherInsertDTO insertDTO) throws Exception {
        try {
            JPAHelper.beginTransaction();

            Teacher teacher = Mapper.mapToTeacher(insertDTO);

            TeacherReadOnlyDTO readOnlyDTO = teacherDAO.insert(teacher)
                    .map(Mapper::mapToTeacherReadOnlyDTO)
                    .orElseThrow(() -> new Exception("Teacher not inserted"));

            JPAHelper.commitTransaction();

            LOGGER.info("Teacher with id {}, lastname {}, firstname {} inserted",
                    teacher.getId(), teacher.getLastname(), teacher.getFirstname());
            return readOnlyDTO;
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            LOGGER.error("Error. Teacher not inserted: firstname: {} , lastname {}",
                    insertDTO.getFirstname(), insertDTO.getLastname());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public TeacherReadOnlyDTO updateTeacher(TeacherUpdateDTO updateDTO) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();

            Teacher teacher = Mapper.mapToTeacher(updateDTO);

            TeacherReadOnlyDTO readOnlyDTO = teacherDAO.update(teacher)
                    .map(Mapper::mapToTeacherReadOnlyDTO)
                    .orElseThrow(() -> new EntityNotFoundException("Teacher not updated"));

            JPAHelper.commitTransaction();

            LOGGER.info("Teacher with id {}, lastname {}, firstname {} inserted",
                    teacher.getId(), teacher.getLastname(), teacher.getFirstname());
            return readOnlyDTO;
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            LOGGER.error("Error. Teacher not updated: id {}, firstname: {} , lastname {}",
                    updateDTO.getId(), updateDTO.getFirstname(), updateDTO.getLastname());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public void deleteTeacher(Object id) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();
            teacherDAO.getById(id).orElseThrow(() -> new EntityNotFoundException(Teacher.class, (Long) id));
            teacherDAO.delete(id);
            JPAHelper.commitTransaction();
            LOGGER.info("Teacher with id {} was deleted", id);
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            LOGGER.error("Error. Teacher with id {} was not deleted", id);
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }

    }

    @Override
    public TeacherReadOnlyDTO getTeacherById(Object id) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();

            TeacherReadOnlyDTO readOnlyDTO = teacherDAO.getById(id)
                    .map(Mapper::mapToTeacherReadOnlyDTO)
                    .orElseThrow(() -> new EntityNotFoundException("Teacher with id " + id + " not found"));
            JPAHelper.commitTransaction();
            //LOGGER.info("Teacher with id {} was found", id);
            return readOnlyDTO;
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            LOGGER.warn("Error. Teacher with id {} was not found", id);
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<TeacherReadOnlyDTO> getAllTeachers() {
        return null;
    }

    @Override
    public List<TeacherReadOnlyDTO> getTeachersByCriteria(Map<String, Object> criteria) {
        return null;
    }
}
