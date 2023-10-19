package com.example.todo.controller;

import com.example.todo.entity.List;
import com.example.todo.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ListController {
    @Autowired
    private ListService service;
    @PostMapping("/addList")
    public List addList(@RequestBody List list) {
        return service.saveList(list);
    }

    @PostMapping("/addLists")
    public java.util.List<List> addLists(@RequestBody java.util.List<List> lists) {
        return service.saveLists(lists);
    }

    @GetMapping("/lists")
    public java.util.List<List> findAllLists() {
        return service.getLists();
    }
    @GetMapping("/list/{id}")

    public List findListById(@PathVariable int id) {
        return service.getListById(id);
    }

    @GetMapping("/list/{hash}")
    public List findListByHash(@PathVariable String hash) {
        return service.getListByHash(hash);
    }

    @PutMapping("/upadate")
    public List updateList(@RequestBody List list) {
        return service.updateList(list);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteList(@PathVariable int id) {
        return service.deleteList(id);
    }
}
