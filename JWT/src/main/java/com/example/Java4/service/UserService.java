package com.example.Java4.service;

import com.example.Java4.entity.User;
import com.example.Java4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository.findByUsername(username).orElseThrow( () -> new UsernameNotFoundException("user is not valid"));
    }

    public User saveUser(User user) {
        return repository.save(user);
    }

    public List<User> saveUsers(List<User> users) {
        return repository.saveAll(users);
    }

    public User getUserById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public String deleteUser(int id) {
        repository.deleteById(id);
        return "deleted user" + id;
    }

    public User updateUser(User user) {
        User existingUser = repository.findById(user.getId()).orElse(null);
//        existingUser.setEmail(user.getEmail());
//        existingUser.setName(user.getName());
        existingUser.setPassword(user.getPassword());
        existingUser.setLists(user.getLists());
        return repository.save(existingUser);
    }
}
