package com.aether.LibraryManagementSystem.controller;

import com.aether.LibraryManagementSystem.entities.Author;
import com.aether.LibraryManagementSystem.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorController {
    private final AuthorService authorService;


    @PostMapping
    public ResponseEntity<List<Author>> addAuthors(@RequestBody List<Author> authors){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authorService.addAuthors(authors));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id){
        Author author=authorService.getAuthorById(id);

        return ResponseEntity.ok(author);
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors(){
        List<Author> authors=authorService.getAllAuthors();

        return ResponseEntity.ok(authors);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        authorService.deleteAuthorById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Successfully Deleted id: "+id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id,@RequestBody Author author){
        Author updatedAuthor= authorService.updateAuthor(id,author);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedAuthor);
    }
}
