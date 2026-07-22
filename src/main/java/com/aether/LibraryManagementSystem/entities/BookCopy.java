package com.aether.LibraryManagementSystem.entities;

import com.aether.LibraryManagementSystem.enums.CopyStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    private String barCode;

    @Enumerated(EnumType.STRING)
    private CopyStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;




}
