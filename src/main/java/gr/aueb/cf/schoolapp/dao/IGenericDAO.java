package gr.aueb.cf.schoolapp.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @param <T>
 */
public interface IGenericDAO<T> {

    Optional<T> insert(T t);
    Optional<T> update(T t);
    void delete(Object id);
    Optional<T> getById(Object id);
    List<T> getAll();
    List<T> getByCriteria(Map<String, Object> criteria);
    List<T> getByCriteria(Class<T> clazz, Map<String, Object> criteria);
}
