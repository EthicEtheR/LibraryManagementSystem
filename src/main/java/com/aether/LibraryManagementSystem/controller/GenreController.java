package com.aether.LibraryManagementSystem.controller;

import com.aether.LibraryManagementSystem.entities.Genre;
import com.aether.LibraryManagementSystem.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
@AllArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<List<Genre>> addGenres(@RequestBody List<Genre> genres){

        List<Genre> genreList = genreService.addGenres(genres);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(genreList);

    }

    @GetMapping
    public ResponseEntity<List<Genre>> getGenres(){
        List<Genre> genres=genreService.getAllGenres();

        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable Long id){

        return ResponseEntity.ok(genreService.getGenre(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable Long id){
        genreService.deleteGenre(id);

        return ResponseEntity.ok("Genre " + id + " deleted successfully.");
    }

    @PutMapping("{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable Long id,@RequestBody Genre genre){

        Genre updated=genreService.updateGenre(id,genre);

        return ResponseEntity.ok(updated);
    }

}
