package com.example.todo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Lists")
public class List {
    @Id
    @GeneratedValue
    private int id;

//    @ManyToMany(mappedBy = "lists")
//    private Set<User> users;

    @OneToMany(mappedBy = "list")
    private Set<UsersLists> users;

    private String hash;

    private Boolean isFavorite;

    @OneToMany(mappedBy = "list")
    private Set<ListItem> listItems;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

}
