package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.service.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Map;

public interface ITeacherService {
    TeacherReadOnlyDTO insertTeacher(TeacherInsertDTO insertDTO) throws Exception;
    TeacherReadOnlyDTO updateTeacher(TeacherUpdateDTO updateDTO) throws EntityNotFoundException;
    void deleteTeacher(Object id) throws EntityNotFoundException;
    TeacherReadOnlyDTO getTeacherById(Object id) throws EntityNotFoundException;
    List<TeacherReadOnlyDTO> getAllTeachers();
    List<TeacherReadOnlyDTO> getTeachersByCriteria(Map<String , Object> criteria);
}
