package com.example.Java4.controller;

import com.example.Java4.entity.List;
import com.example.Java4.entity.ListItem;
import com.example.Java4.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/list")
@RestController
public class ListController {

    @Autowired
    private ListService service;

    @PostMapping("/")  // localhost:8080/list
    public List addList(@RequestBody List list) {
        return service.saveList(list);
    }

    @PostMapping("/s")  // localhost:8080/list/s
    public java.util.List<List> addLists(@RequestBody java.util.List<List> lists) {
        return service.saveLists(lists);
    }

    @GetMapping("/s")   // localhost:8080/list/s
    public java.util.List<List> findAllLists() {
        return service.getLists();
    }

    @GetMapping("/id/{id}")  // localhost:8080/list/id/1
    public List findListById(@PathVariable int id) {
        return service.getListById(id);
    }

    @GetMapping("/hash/{hash}")  // localhost:8080/list/hash/qwer1234QWER!@#$
    public List findListByHash(@PathVariable String hash) {
        return service.getListByHash(hash);
    }

    @PutMapping("/")  // localhost:8080/list
    public List updateList(@RequestBody List list) {
        return service.updateList(list);
    }

    @DeleteMapping("/{id}")  // localhost:8080/list/1
    public String deleteList(@PathVariable int id) {
        return service.deleteList(id);
    }

    @PutMapping("/{listId}/user/{userId}")  // localhost:8080/list/1/user/1
    public List assignListToUser(@PathVariable int userId, @PathVariable int listId) {
        System.out.println("controller");
        return service.assignListToUser(userId, listId);
    }

    @GetMapping("/{listId}/items")  // localhost:8080/list/1/items
    public java.util.List<ListItem> getItemsByListId(@PathVariable int listId) {
        return service.getItemsByListId(listId);
    }
}
