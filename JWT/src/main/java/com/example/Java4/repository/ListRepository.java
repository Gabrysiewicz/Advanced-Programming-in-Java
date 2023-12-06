package com.example.Java4.repository;

import com.example.Java4.entity.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List, Integer> {
    boolean existsByIdAndUsersUsername(int listId, String username);
    boolean existsByIdAndUsersId(int listId, int userId);
//    @Query("SELECT list_id FROM users_lists WHERE user_id = :userId") // Adjust the query according to your entity relationships
//    java.util.List<UserList> getListsForUser(@Param("userId") int userId);
}
