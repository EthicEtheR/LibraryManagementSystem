package com.aether.LibraryManagementSystem.repository;

import com.aether.LibraryManagementSystem.dto.BookAvailabilityDto;
import com.aether.LibraryManagementSystem.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

     boolean existsByIsbn(String isbn);

    Optional<Book> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT DISTINCT b FROM Book b JOIN b.authors a WHERE LOWER(a.authorName) " +
            "LIKE LOWER(CONCAT('%', :name, '%'))")
    Optional<Book> findBookByAuthorName(String name);


}
