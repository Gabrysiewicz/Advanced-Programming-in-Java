package com.example.todo.controller;

import com.example.todo.entity.ListItem;
import com.example.todo.service.ListItemService;
import com.example.todo.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ListItemController {
    @Autowired
    ListItemService listItemService;

    @PostMapping("/addListItem")
    public ListItem addListItem(@RequestBody ListItem listItem) {
        return listItemService.saveListItem(listItem);
    }

    @PutMapping("/items/{itemId}/assign-to-list/{listId}")
    public ListItem assignToList(@PathVariable int itemId, @PathVariable int listId) {
        return listItemService.assignItemToList(itemId, listId);
    }

    @GetMapping("/listItems")
    public List<ListItem> getListItems() {
        return listItemService.getListItems();
    }
}
