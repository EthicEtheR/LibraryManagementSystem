package com.aether.LibraryManagementSystem.repository;

import com.aether.LibraryManagementSystem.entities.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy,Long> {
}
