package com.gmail.petrikov05.app.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.gmail.petrikov05.app.repository.UserRepository;
import com.gmail.petrikov05.app.repository.constant.RoleEnum;
import com.gmail.petrikov05.app.repository.model.User;
import org.springframework.stereotype.Repository;

import static com.gmail.petrikov05.app.repository.constant.TableUserConstant.PASSWORD;
import static com.gmail.petrikov05.app.repository.constant.TableUserConstant.ROLE;
import static com.gmail.petrikov05.app.repository.constant.TableUserConstant.USERNAME;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<User> implements UserRepository {

    @Override
    public User findByUsername(Connection connection, String username) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT username, password, role FROM user WHERE username = ?;"
        )) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getUser(resultSet);
            }
        }
        return null;
    }

    @Override
    public void add(Connection connection, User user) throws SQLException {
        throw new UnsupportedOperationException("Method not use");
    }

    @Override
    public List<User> findAll(Connection connection) throws SQLException {
        throw new UnsupportedOperationException("Method not use");
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUsername(resultSet.getString(USERNAME));
        user.setPassword(resultSet.getString(PASSWORD));
        user.setRole(RoleEnum.valueOf(resultSet.getString(ROLE)));
        return user;
    }

}
