package com.example.todo.repository;

import com.example.todo.entity.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List, Integer> {

    List findByHash(String hash);
}
