package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.IdentifiableEntity;
import gr.aueb.cf.schoolapp.service.util.JPAHelper;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @param <T>
 */
public abstract class AbstractDAO<T extends IdentifiableEntity> implements IGenericDAO<T> {

    private Class<T> persistenceClass;

    public AbstractDAO() {

    }

    public Class<T> getPersistenceClass() {
        return persistenceClass;
    }

    public void setPersistenceClass(Class<T> persistenceClass) {
        this.persistenceClass = persistenceClass;
    }

    @Override
    public Optional<T> insert(T t) {
        EntityManager em = getEntityManager();
        em.persist(t);
        return Optional.of(t);
    }

    @Override
    public Optional<T> update(T t) {
        return Optional.empty();
    }

    @Override
    public void delete(Object id) {

    }

    @Override
    public Optional<T> getById(Object id) {
        return Optional.empty();
    }

    @Override
    public List<T> getAll() {
        return null;
    }

    @Override
    public List<? extends T> getByCriteria(Map<String, Object> criteria) {
        return null;
    }

    @Override
    public <K extends T> List<K> getByCriteria(Class<K> clazz, Map<String, Object> criteria) {
        return null;
    }

    public EntityManager getEntityManager() {
        return JPAHelper.getEntityManager();
    }
}
