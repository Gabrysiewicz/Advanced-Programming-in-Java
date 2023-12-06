package com.example.Java4.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
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
@Builder
public class List {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    private Boolean isFavorite;

    @JsonManagedReference
    @EqualsAndHashCode.Exclude()
    @ToString.Exclude
    @OneToMany(mappedBy = "list", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<ListItem> listItems =  new HashSet<>();

    @ManyToMany(mappedBy = "lists", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<User> users =  new HashSet<>();

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
