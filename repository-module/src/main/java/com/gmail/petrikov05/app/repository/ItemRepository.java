package com.gmail.petrikov05.app.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.petrikov05.app.repository.model.Item;

public interface ItemRepository extends GenericRepository<Item> {

    List<Item> findItemByStatus(Connection connection, String status) throws SQLException;

    void updateStatusById(Connection connection, Long id, String status) throws SQLException;

    void deleteById(Connection connection, String status) throws SQLException;

}
