package gr.aueb.cf.schoolapp.mapper;

import gr.aueb.cf.schoolapp.dto.TeacherFiltersDTO;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.model.Teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Mapper {

    private Mapper() {}

    public static Teacher mapToTeacher(TeacherInsertDTO dto) {
        return new Teacher(null, dto.getVat(), dto.getFirstname(), dto.getLastname());
    }

    public static Teacher mapToTeacher(TeacherUpdateDTO dto) {
        return new Teacher(dto.getId(), dto.getVat(), dto.getFirstname(), dto.getLastname());
    }

    public static TeacherReadOnlyDTO mapToTeacherReadOnlyDTO(Teacher teacher) {
        return new TeacherReadOnlyDTO(teacher.getId(), teacher.getVat(), teacher.getFirstname(), teacher.getLastname());
    }

    public static List<TeacherReadOnlyDTO> teachersToReadOnlyDTOs(List<Teacher> teachers) {
        return teachers.stream().map(Mapper::mapToTeacherReadOnlyDTO).collect(Collectors.toList());
    }

    public static Map<String , Object> mapToCriteria(TeacherFiltersDTO filtersDTO) {
        Map<String , Object> filters = new HashMap<>();

        if (filtersDTO.getFirstname() != null && !filtersDTO.getFirstname().isEmpty()) {
            filters.put("firstname", filtersDTO.getFirstname());
        }

        if (filtersDTO.getLastname() != null && !filtersDTO.getLastname().isEmpty()) {
            filters.put("lastname", filtersDTO.getLastname());
        }

        if (filtersDTO.getVat() != null && !filtersDTO.getVat().isEmpty()) {
            filters.put("vat", filtersDTO.getVat());
        }
        return filters;
    }
}
