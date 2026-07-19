package com.aether.LibraryManagementSystem.service;

import com.aether.LibraryManagementSystem.entities.Author;
import com.aether.LibraryManagementSystem.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public  List<Author> addAuthors(List<Author> authors) {
        //TODO return DTOs
        return authorRepository.saveAll(authors);
    }

    public Author getAuthorById(Long id) {

        if(authorRepository.existsById(id))
          return   authorRepository.getReferenceById(id);

        throw new RuntimeException("Author not found ");
    }

    public List<Author> getAllAuthors() {

        return authorRepository.findAll();
    }

    public void deleteAuthorById(Long id) {

        if(authorRepository.existsById(id))
            authorRepository.deleteById(id);

    }

    public Author updateAuthor(Long id, Author author) {
        Author updatedAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        updatedAuthor.setAuthorName(author.getAuthorName());
        updatedAuthor.setBio(author.getBio());

       return  authorRepository.save(updatedAuthor);

    }
}
