package com.aether.LibraryManagementSystem.service;

import com.aether.LibraryManagementSystem.dto.BookResponse;
import com.aether.LibraryManagementSystem.entities.Author;
import com.aether.LibraryManagementSystem.entities.Book;
import com.aether.LibraryManagementSystem.entities.Genre;
import com.aether.LibraryManagementSystem.entities.Publisher;
import com.aether.LibraryManagementSystem.exception.ResourceNotFoundException;
import com.aether.LibraryManagementSystem.repository.AuthorRepository;
import com.aether.LibraryManagementSystem.repository.BookRepository;
import com.aether.LibraryManagementSystem.repository.GenreRepository;
import com.aether.LibraryManagementSystem.repository.PublisherRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;


    @Transactional
    public BookResponse addBook(Book book){

        log.info("In addBook method");

        //1.validating Publisher and null check
        if (book.getPublisher() == null || book.getPublisher().getId() == null) {
            throw new IllegalArgumentException("Publisher id is required.");
        }
        Publisher publisher = publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Publisher not found with id : "+
                                book.getPublisher().getId()));




        // 2. Validate authors

       //Extract id which are send from client
        List<Long> authorIds = book.getAuthors()
                .stream()
                .map(Author::getId)
                .toList();

        //null check
        if(book.getAuthors() == null)
            throw new IllegalArgumentException("Author ids is required ");


        //find authors which ids are present in authorIds
        Set<Author> authors = new HashSet<>(authorRepository.findAllById(authorIds));

        //extract ids which are found in client request
        Set<Long> foundIds = authors.stream()
                .map(Author::getId)
                .collect(Collectors.toSet());


        List<Long> missingIds = authorIds.stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        if (!missingIds.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Authors not found: " + missingIds);
        }

        List<String> authorNames = authors.stream()
                .map(Author::getAuthorName)
                .toList();




        // 3. Validate genres
        List<Long> genreIds=book.getGenres()
                .stream()
                .map(Genre::getId)
                .toList();

        //null check
        if(book.getGenres()==null )
            throw new IllegalArgumentException("Genre is required");

        List<Genre> genres= new ArrayList<>(genreRepository.findAllById(genreIds));

        Set<Long> foundGenreIds = genres.stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());

        List<Long> missingGenreIds = genreIds.stream()
                .filter(id -> !foundGenreIds.contains(id))
                .toList();

        if (!missingGenreIds.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Genres not found: " + missingGenreIds);
        }

        List<String> genreName= genres.stream()
                        .map(Genre::getName)
                                .toList();

        if(bookRepository.existsByIsbn(book.getIsbn()))
            throw new IllegalArgumentException("Isbn number is already present ");
        // 4. Set managed entities into book
        book.setAuthors(authors);
        book.setGenres(new HashSet<>(genres));
        book.setPublisher(publisher);

        // 5. Save book
        Book saved = bookRepository.save(book);

        // 6. Build response
        BookResponse response = new BookResponse();

        response.setTitle(saved.getTitle());
        response.setIsbn(saved.getIsbn());
        response.setPublisher(publisher.getPublisherName());

        response.setGenres(genreName);
        response.setAuthors(authorNames);

        log.info(response.toString());

        return response;
    }


    public void deleteBook(Long id) {
        if(bookRepository.existsById(id))
            bookRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("Book not Found");
    }
}
