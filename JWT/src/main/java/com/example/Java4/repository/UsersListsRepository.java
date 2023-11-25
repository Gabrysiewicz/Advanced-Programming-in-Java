package com.example.Java4.repository;

import com.example.Java4.entity.UserList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersListsRepository extends JpaRepository<UserList, Long> {

    // Custom method to check if a record exists by user ID and list ID
    boolean existsByUserIdAndListId(int userId, int listId);
}