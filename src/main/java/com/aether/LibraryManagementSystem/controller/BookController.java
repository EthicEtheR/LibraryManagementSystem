package com.aether.LibraryManagementSystem.controller;

import com.aether.LibraryManagementSystem.dto.BookResponse;
import com.aether.LibraryManagementSystem.entities.Book;
import com.aether.LibraryManagementSystem.entities.Member;
import com.aether.LibraryManagementSystem.service.BookService;
import com.aether.LibraryManagementSystem.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final  BookService bookService;



    @PostMapping
    public ResponseEntity<BookResponse> addABook(@RequestBody Book book){

        BookResponse resBook= bookService.addBook(book);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resBook);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id){
         bookService.deleteBook(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Book: "+id+" is deleted successfully");
    }



}
