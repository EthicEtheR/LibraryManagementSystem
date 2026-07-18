package com.aether.LibraryManagementSystem.service;

import com.aether.LibraryManagementSystem.entities.Book;
import com.aether.LibraryManagementSystem.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book addingABook(Book book){

        return bookRepository.save(book);
    }




}
