package com.david.api_test.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}