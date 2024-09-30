package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.Teacher;

import java.util.Optional;

public interface ITeacherDAO extends IGenericDAO<Teacher> {
    Optional<Teacher> getByVat(String vat);
}
