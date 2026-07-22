package com.aether.LibraryManagementSystem.repository;

import com.aether.LibraryManagementSystem.entities.Book;
import com.aether.LibraryManagementSystem.entities.BookCopy;
import com.aether.LibraryManagementSystem.enums.CopyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy,Long> {

    Optional<BookCopy> findFirstByBookAndStatus(Book book, CopyStatus status);
}
