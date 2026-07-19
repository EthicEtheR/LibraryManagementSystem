package com.aether.LibraryManagementSystem.service;

import com.aether.LibraryManagementSystem.entities.Genre;
import com.aether.LibraryManagementSystem.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public List<Genre> addGenres(List<Genre> genres) {

        return genreRepository.saveAll(genres);
    }

    public List<Genre> getAllGenres() {

        return genreRepository.findAll();
    }

    public @Nullable Genre getGenre(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Genre not found "));
    }

    public void deleteGenre(Long id) {
        if(genreRepository.existsById(id))
            genreRepository.deleteById(id);
        else
            throw new RuntimeException("Genre not found");
    }

    public Genre updateGenre(Long id, Genre genre) {
        Genre updated=genreRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Genre not found "));

        updated.setName(genre.getName());

        return genreRepository.save(updated);
    }
}
