package com.aether.LibraryManagementSystem.service;

import com.aether.LibraryManagementSystem.dto.BookResponse;
import com.aether.LibraryManagementSystem.entities.Author;
import com.aether.LibraryManagementSystem.entities.Book;
import com.aether.LibraryManagementSystem.entities.Genre;
import com.aether.LibraryManagementSystem.entities.Publisher;
import com.aether.LibraryManagementSystem.repository.AuthorRepository;
import com.aether.LibraryManagementSystem.repository.BookRepository;
import com.aether.LibraryManagementSystem.repository.GenreRepository;
import com.aether.LibraryManagementSystem.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;


    public BookResponse addingABook(Book book){

        Book saved = bookRepository.save(book);

        BookResponse response = new BookResponse();

        response.setTitle(saved.getTitle());
        response.setIsbn(saved.getIsbn());

    //TODO Exceptin handling if id which are coming from client are not in Db

        Publisher publisher = publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));

        response.setPublisher(publisher.getPublisherName());

        Set<Author> authors = new HashSet<>(
                authorRepository.findAllById(
                        book.getAuthors().stream()
                                .map(Author::getId)
                                .toList()
                )
        );

        List<String> authorNames = authors.stream()
                .map(Author::getAuthorName)
                .toList();
        response.setAuthors(authorNames);

        Set<Genre> genres = new HashSet<>(
                genreRepository.findAllById(
                        book.getGenres().stream()
                                .map(Genre::getId)
                                .toList()
                )
        );

        List<String> genreName= genres.stream()
                        .map(Genre::getName)
                                .toList();

        response.setGenres(genreName);

        log.info(response.toString());

        return response;
    }


    public void deleteBook(Long id) {
        if(bookRepository.existsById(id))
            bookRepository.deleteById(id);
        else
            throw new RuntimeException("Book not Found");
    }
}
