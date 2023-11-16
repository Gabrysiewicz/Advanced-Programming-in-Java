package com.example.Java4.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

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
