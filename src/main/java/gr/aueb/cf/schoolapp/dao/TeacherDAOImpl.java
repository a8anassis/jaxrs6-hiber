package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.Teacher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.ext.Provider;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

@ApplicationScoped
public class TeacherDAOImpl extends AbstractDAO<Teacher> implements ITeacherDAO {

    public TeacherDAOImpl() {
        this.setPersistenceClass(Teacher.class);
    }

    @Override
    public Optional<Teacher> getByVat(String vat) {
        EntityManager em = getEntityManager();
        String sql = "SELECT t FROM Teacher t WHERE t.vat = :vat";

        try {
            Teacher teacher = em.createQuery(sql, Teacher.class)
                    .setParameter("vat", vat)
                    .getSingleResult();
            return Optional.of(teacher);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
