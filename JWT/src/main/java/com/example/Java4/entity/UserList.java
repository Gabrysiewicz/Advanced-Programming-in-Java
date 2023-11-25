package com.example.Java4.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserList {

    @Id
    private Long id;

    private String userId;

    private int listId;

    // Getters and setters...

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }
}

