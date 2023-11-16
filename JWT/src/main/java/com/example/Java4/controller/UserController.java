package com.example.Java4.controller;

import com.example.Java4.entity.User;
import com.example.Java4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    @PostMapping("/users")
    public List<User> addUsers(@RequestBody List<User> users) {
        return service.saveUsers(users);
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return service.getUsers();
    }

    @GetMapping("/user/{id}")
    public User findUserById(@PathVariable int id) {
        return service.getUserById(id);
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User user) {
        return service.updateUser(user);
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable int id) {
        return service.deleteUser(id);
    }

}
