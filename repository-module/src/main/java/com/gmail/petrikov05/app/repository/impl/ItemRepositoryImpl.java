package com.gmail.petrikov05.app.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gmail.petrikov05.app.repository.ItemRepository;
import com.gmail.petrikov05.app.repository.model.Item;
import org.springframework.stereotype.Repository;

import static com.gmail.petrikov05.app.repository.constant.TableItemConstant.ID;
import static com.gmail.petrikov05.app.repository.constant.TableItemConstant.NAME;
import static com.gmail.petrikov05.app.repository.constant.TableItemConstant.STATUS;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Item> implements ItemRepository {

    @Override
    public List<Item> findAll(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id, name, status FROM item;"
        )) {
            ResultSet resultSet = statement.executeQuery();
            List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                items.add(getItem(resultSet));
            }
            return items;
        }
    }

    @Override
    public List<Item> findItemByStatus(Connection connection, String status) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id, name, status FROM item WHERE status = ?;"
        )) {
            statement.setString(1, String.valueOf(status));
            ResultSet resultSet = statement.executeQuery();
            List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                items.add(getItem(resultSet));
            }
            return items;
        }
    }

    @Override
    public void updateStatusById(Connection connection, Long id, String status) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE item SET status = ? WHERE id = ?;"
        )) {
            statement.setString(1, status);
            statement.setLong(2, id);
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteById(Connection connection, String status) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM item WHERE status = ?;"
        )) {
            statement.setString(1, status);
            statement.executeUpdate();
        }
    }

    @Override
    public void add(Connection connection, Item item) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO item(name, status) VALUE(?, ?);"
        )) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getStatus());
            statement.executeUpdate();
        }
    }

    private Item getItem(ResultSet resultSet) throws SQLException {
        Item item = new Item();
        item.setId(resultSet.getLong(ID));
        item.setName(resultSet.getString(NAME));
        item.setStatus(resultSet.getString(STATUS));
        return item;
    }

}
