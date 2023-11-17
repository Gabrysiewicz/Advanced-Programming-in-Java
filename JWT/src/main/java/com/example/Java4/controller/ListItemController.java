package com.example.Java4.controller;

import com.example.Java4.entity.ListItem;
import com.example.Java4.service.ListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ListItemController {
    @Autowired
    ListItemService listItemService;

    @PostMapping("/item")
    public ListItem addListItem(@RequestBody ListItem listItem) {
        return listItemService.saveListItem(listItem);
    }

    @PutMapping("/items/{itemId}/list/{listId}")
    public ListItem assignToList(@PathVariable int itemId, @PathVariable int listId) {
        return listItemService.assignItemToList(itemId, listId);
    }

    @GetMapping("/items")
    public List<ListItem> getListItems() {
        return listItemService.getListItems();
    }

    @DeleteMapping("/item/{id}")
    public String deleteItemById(@PathVariable int id) {
        return listItemService.deleteItemById(id);
    }

    @GetMapping("/item/{id}")
    public ListItem getItemById(@PathVariable int id) {
        return listItemService.getItemById(id);
    }

    @PutMapping("/item")
    public ListItem updateListItem(@RequestBody ListItem item) {
        return listItemService.updateListItem(item);
    }
}