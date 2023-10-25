package com.example.todo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.YesNoConverter;

import java.util.Date;
import java.util.Objects;

// @Data in both models results in recursion
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ListItems")
public class ListItem
{
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "list_id")
    private List list;

    private String name;

    private Boolean isDone;

    private String description;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;



}
