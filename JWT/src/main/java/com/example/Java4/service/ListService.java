package com.example.Java4.service;

import com.example.Java4.entity.List;
import com.example.Java4.entity.ListItem;
import com.example.Java4.entity.User;
import com.example.Java4.repository.ListRepository;
import com.example.Java4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListService {
    @Autowired
    private ListRepository repository;

    @Autowired
    private UserRepository userRepository;


    public List saveList(List list) {
        return repository.save(list);
    }

    public java.util.List<List> saveLists(java.util.List<List> lists) {
        return repository.saveAll(lists);
    }

    public java.util.List<List> getLists() {
        return repository.findAll();
    }

    public List getListById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List getListByHash(String hash) {
        return repository.findByHash(hash);
    }

    public String deleteList(int id) {
        repository.deleteById(id);
        return "list removed" + id;
    }

    public List updateList(List list) {
        List existing = repository.findById(list.getId()).orElse(null);
//        assert existing != null;
        existing.setHash(list.getHash());
        existing.setIsFavorite(list.getIsFavorite());
        return repository.save(existing);
    }

    public java.util.List<ListItem> getItemsByListId(int listId) {
        List existing = repository.findById(listId).orElse(null);
        return existing.getListItems().stream().toList();
    }

    public List assignListToUser(int userId, int listId) {
        System.out.println("service");

        User user = userRepository.findById(userId).orElse(null);
        List list = repository.findById(listId).orElse(null);

        user.addList(list);
        list.addUser(user);
        System.out.println("dodano");

//        userRepository.save(user);
        System.out.println("zapisano");

        return repository.save(list);

    }
}
