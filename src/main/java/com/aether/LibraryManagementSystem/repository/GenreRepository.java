package com.aether.LibraryManagementSystem.repository;

import com.aether.LibraryManagementSystem.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {
}
