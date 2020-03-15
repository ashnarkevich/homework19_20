package com.gmail.petrikov05.app.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.gmail.petrikov05.app.repository.impl.ItemRepositoryImpl;
import com.gmail.petrikov05.app.repository.model.Item;
import com.gmail.petrikov05.app.service.ItemService;
import com.gmail.petrikov05.app.service.model.ItemDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LogManager.getLogger(ItemServiceImpl.class);
    private final ItemRepositoryImpl itemRepository;

    public ItemServiceImpl(ItemRepositoryImpl itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<ItemDTO> showItemByRole() {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Item> items = getItemsByRole(connection);
                List<ItemDTO> itemsDTO = items.stream()
                        .map(this::convertObjectToDTO)
                        .collect(Collectors.toList());
                connection.commit();
                return itemsDTO;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<ItemDTO> findAll() {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Item> items = itemRepository.findAll(connection);
                List<ItemDTO> itemsDTO = items.stream()
                        .map(this::convertObjectToDTO)
                        .collect(Collectors.toList());
                connection.commit();
                return itemsDTO;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public Boolean saveItem(ItemDTO itemDTO) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = convertDTOToObject(itemDTO);
                itemRepository.add(connection, item);
                connection.commit();
                return true;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public Boolean updateStatusByID(Long id, String status) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                itemRepository.updateStatusById(connection, id, status);
                connection.commit();
                return true;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public Boolean deleteItemByStatus(String status) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                itemRepository.deleteById(connection, status);
                connection.commit();
                return true;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    private List<Item> getItemsByRole(Connection connection) throws SQLException {
        String role = getRole();
        if (role != null) {
            switch (role) {
                case "ROLE_ADMIN": {
                    return itemRepository.findAll(connection);
                }
                case "ROLE_USER": {
                    String status = "COMPLETE";
                    return itemRepository.findItemByStatus(connection, status);
                }
                default: {
                    throw new UnsupportedOperationException("Role not found");
                }
            }
        } else {
            throw new UnsupportedOperationException("Role not found");
        }
    }

    private String getRole() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities();
        for (GrantedAuthority role : authorities) {
            return role.getAuthority();
        }
        return null;
    }

    private Item convertDTOToObject(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setStatus(itemDTO.getStatus());
        return item;
    }

    private ItemDTO convertObjectToDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName(item.getName());
        itemDTO.setStatus(item.getStatus());
        return itemDTO;
    }

}
