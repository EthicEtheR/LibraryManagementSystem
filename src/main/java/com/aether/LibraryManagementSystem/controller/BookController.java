package com.aether.LibraryManagementSystem.controller;

import com.aether.LibraryManagementSystem.dto.BookAvailabilityDto;
import com.aether.LibraryManagementSystem.dto.BookResponse;
import com.aether.LibraryManagementSystem.entities.Book;
import com.aether.LibraryManagementSystem.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<BookResponse> findBookByTitle(@RequestParam String title){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.findBookWithTitle(title));
    }

   

}
