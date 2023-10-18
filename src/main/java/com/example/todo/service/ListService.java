package com.example.todo.service;

import com.example.todo.entity.List;
import com.example.todo.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListService {
    @Autowired
    private ListRepository repository;

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
}
