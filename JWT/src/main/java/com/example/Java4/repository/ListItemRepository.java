package com.example.Java4.repository;

import com.example.Java4.entity.ListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListItemRepository extends JpaRepository<ListItem, Integer> {
    boolean existsByIdAndListId(int itemId, int listId);

}
