package com.example.todo.controller;

import com.example.todo.entity.List;
import com.example.todo.entity.ListItem;
import com.example.todo.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class ListController {

    @Autowired
    private ListService service;

    @PostMapping("/list")
    public List addList(@RequestBody List list) {
        return service.saveList(list);
    }

    @PostMapping("/lists")
    public java.util.List<List> addLists(@RequestBody java.util.List<List> lists) {
        return service.saveLists(lists);
    }

    @GetMapping("/lists")
    public java.util.List<List> findAllLists() {
        return service.getLists();
    }

    @GetMapping("/list/id/{id}")
    public List findListById(@PathVariable int id) {
        return service.getListById(id);
    }

    @GetMapping("/list/hash/{hash}")
    public List findListByHash(@PathVariable String hash) {
        return service.getListByHash(hash);
    }

    @PutMapping("/list")
    public List updateList(@RequestBody List list) {
        return service.updateList(list);
    }

    @DeleteMapping("/list/{id}")
    public String deleteList(@PathVariable int id) {
        return service.deleteList(id);
    }

    @PutMapping("user/{userId}/list/{listId}")
    public List assignListToUser(@PathVariable int userId, @PathVariable int listId) {
        System.out.println("controller");
        return service.assignListToUser(userId, listId);
    }

    @GetMapping("/list/{listId}/items")
    public java.util.List<ListItem> getItemsByListId(@PathVariable int listId) {
        return service.getItemsByListId(listId);
    }
}
