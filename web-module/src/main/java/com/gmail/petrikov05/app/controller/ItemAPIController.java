package com.gmail.petrikov05.app.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import com.gmail.petrikov05.app.service.ItemService;
import com.gmail.petrikov05.app.service.model.ItemDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
public class ItemAPIController {

    private final ItemService itemService;

    public ItemAPIController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemDTO> showItem() {
        return itemService.findAll();
    }

    @PostMapping
    public ResponseEntity<Object> saveItem(@RequestBody @Valid ItemDTO itemDTO, BindingResult bindingResult) {
        Boolean isSave = itemService.saveItem(itemDTO);
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            List<String> errors = new ArrayList<>();
            for (ObjectError error : allErrors) {
                errors.add(error.getDefaultMessage());
            }
            return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(isSave, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Boolean updateStatusByID(@PathVariable Long id, @RequestParam String status) {
        return itemService.updateStatusByID(id, status);
    }

    @DeleteMapping
    public Boolean deleteItemByStatus(@RequestParam String status) {
        return itemService.deleteItemByStatus(status);
    }

}
