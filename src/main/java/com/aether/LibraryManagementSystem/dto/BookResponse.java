package com.aether.LibraryManagementSystem.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class BookResponse {

    private String title;

    private String isbn;

    private String publisher;

    private List<String> authors;

    private List<String> genres;
}
