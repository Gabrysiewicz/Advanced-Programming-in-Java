package com.example.todo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue
    private int id;

//    @ManyToMany(cascade = { CascadeType.ALL })
//    private Set<List> lists;
    @OneToMany(mappedBy = "user")
    private Set<UsersLists> lists;

    private String email;
    private String password;
    private String name;
    private String surname;
    private String profilePicture;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;



}
