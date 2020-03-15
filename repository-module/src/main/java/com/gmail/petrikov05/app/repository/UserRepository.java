package com.gmail.petrikov05.app.repository;

import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.petrikov05.app.repository.model.User;

public interface UserRepository extends GenericRepository<User> {

    User findByUsername(Connection connection, String username) throws SQLException;

}
