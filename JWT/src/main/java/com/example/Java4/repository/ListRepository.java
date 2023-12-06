package com.example.Java4.repository;

import com.example.Java4.entity.List;
import com.example.Java4.entity.UserList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ListRepository extends JpaRepository<List, Integer> {
    List findByHash(String hash);
    boolean existsByIdAndUsersUsername(int listId, String username);
    boolean existsByIdAndUsersId(int listId, int userId);
//    @Query("SELECT list_id FROM users_lists WHERE user_id = :userId") // Adjust the query according to your entity relationships
//    java.util.List<UserList> getListsForUser(@Param("userId") int userId);
}
