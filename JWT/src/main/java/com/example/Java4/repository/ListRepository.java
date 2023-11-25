package com.example.Java4.repository;

import com.example.Java4.entity.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List, Integer> {
    List findByHash(String hash);
    boolean existsByIdAndUsersUsername(int listId, String username);

    boolean existsByIdAndUsersId(int listId, int userId);


}
