package com.gmail.petrikov05.app.service;

import java.util.List;

import com.gmail.petrikov05.app.service.model.ItemDTO;

public interface ItemService {

    List<ItemDTO> showItemByRole();

    List<ItemDTO> findAll();

    Boolean saveItem(ItemDTO item);

    Boolean updateStatusByID(Long id, String status);

    Boolean deleteItemByStatus(String status);

}
