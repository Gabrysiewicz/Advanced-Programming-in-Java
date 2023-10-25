package com.example.todo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
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


    private String hash;

    private Boolean isFavorite;

    @JsonManagedReference
    @EqualsAndHashCode.Exclude()
    @ToString.Exclude
    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ListItem> listItems;

    @ManyToMany(mappedBy = "lists", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
//    @JsonBackReference
    private Set<User> users;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public void addListItem(ListItem listItem) {
        listItems.add(listItem);
    }

    public void addUser(User user) {
        users.add(user);
    }


}
