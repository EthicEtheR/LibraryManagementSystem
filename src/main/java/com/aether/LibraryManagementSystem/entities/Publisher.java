package com.aether.LibraryManagementSystem.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String publisherName;


}

