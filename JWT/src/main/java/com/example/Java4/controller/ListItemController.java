package com.example.Java4.controller;

import com.example.Java4.entity.ListItem;
import com.example.Java4.service.ListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/item")
@RestController
public class ListItemController {
    @Autowired
    ListItemService listItemService;

    @PostMapping("/") // localhost:8080/item
    public ListItem addListItem(@RequestBody ListItem listItem) {
        return listItemService.saveListItem(listItem);
    }

    @PutMapping("/{itemId}/list/{listId}")  // localhost:8080/item/1/list/1
    public ListItem assignToList(@PathVariable int itemId, @PathVariable int listId) {
        return listItemService.assignItemToList(itemId, listId);
    }

    @GetMapping("/s")  // localhost:8080/items
    public List<ListItem> getListItems() {
        return listItemService.getListItems();
    }

    @DeleteMapping("/{id}")  // localhost:8080/item/1
    public String deleteItemById(@PathVariable int id) {
        return listItemService.deleteItemById(id);
    }

    @GetMapping("/{id}")  // localhost:8080/item/1
    public ListItem getItemById(@PathVariable int id) {
        return listItemService.getItemById(id);
    }

    @PutMapping("/")  // localhost:8080/item
    public ListItem updateListItem(@RequestBody ListItem item) {
        return listItemService.updateListItem(item);
    }
}