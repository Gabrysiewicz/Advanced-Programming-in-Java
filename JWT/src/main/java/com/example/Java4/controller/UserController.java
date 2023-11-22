package com.example.Java4.controller;

import com.example.Java4.entity.User;
import com.example.Java4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/") // localhost:8080/user
    public User addUser(@RequestBody User user) {
        return service.saveUser(user);
    }


    // todo: zabierz my to, tylko dla admina
    @PostMapping("/s")  // localhost:8080/user/s
    public List<User> addUsers(@RequestBody List<User> users) {
        return service.saveUsers(users);
    }

    @GetMapping("/s")  // localhost:8080/user/s
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAllUsers() {
        return service.getUsers();
    }

    @GetMapping("/{id}")  // localhost:8080/user/1
    public User findUserById(@PathVariable int id) {
        return service.getUserById(id);
    }

    @PutMapping("/")  // localhost:8080/user
    public User updateUser(@RequestBody User user) {
        return service.updateUser(user);
    }

    @DeleteMapping("/{id}")  // localhost:8080/user/1
    public String deleteUser(@PathVariable int id) {
        return service.deleteUser(id);
    }

}
