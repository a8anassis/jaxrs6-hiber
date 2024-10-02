package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.User;

import java.util.Optional;

public interface IUserDAO extends IGenericDAO<User> {
    Optional<User> getByUsername(String username);
    boolean isUserValid(String username, String password);
    boolean isEmailExists(String username);
}
