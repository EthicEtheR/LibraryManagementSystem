package com.aether.LibraryManagementSystem.repository;

import com.aether.LibraryManagementSystem.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
}
