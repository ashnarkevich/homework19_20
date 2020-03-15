package com.gmail.petrikov05.app.controller;

import java.util.List;

import com.gmail.petrikov05.app.service.ItemService;
import com.gmail.petrikov05.app.service.model.ItemDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {this.itemService = itemService;}

    @GetMapping
    public String showItems(Model model) {
        List<ItemDTO> items = itemService.showItemByRole();
        model.addAttribute("items", items);
        return "items";
    }

}
