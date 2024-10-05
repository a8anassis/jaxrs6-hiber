package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.security.SecUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

import java.util.Optional;

@ApplicationScoped
public class UserDAOImpl extends AbstractDAO<User> implements IUserDAO {

    @Override
    public Optional<User> getByUsername(String username) {
        String sql = "SELECT u FROM User u WHERE u.username = :username";

        try {
            User user = getEntityManager()
                    .createQuery(sql, User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean isUserValid(String username, String password) {
        String sql = "SELECT u FROM User u WHERE u.username = :username";

        try {
            User user = getEntityManager()
                    .createQuery(sql, User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return SecUtil.checkPassword(password, user.getPassword());
        } catch (NoResultException e) {
           return false;
        }
    }

    @Override
    public boolean isEmailExists(String username) {
        String sql = "SELECT COUNT(u) FROM User u WHERE u.username = :username";

        try {
            Long count = getEntityManager()
                    .createQuery(sql, Long.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return count > 0;
        } catch (NoResultException e) {
            return false;
        }
    }
}
