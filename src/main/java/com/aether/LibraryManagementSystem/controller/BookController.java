package com.aether.LibraryManagementSystem.controller;

import com.aether.LibraryManagementSystem.entities.Book;
import com.aether.LibraryManagementSystem.entities.Member;
import com.aether.LibraryManagementSystem.service.BookService;
import com.aether.LibraryManagementSystem.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BookController {


    private final  BookService bookService;


    private final MemberService memberService;

    @PostMapping("/book")
    public ResponseEntity<Book> addABook(@RequestBody Book book){

        Book resBook= bookService.addingABook(book);
        return ResponseEntity.ok(resBook);
    }

    @PostMapping("/members")
    public ResponseEntity<Member> registerMember(@RequestBody Member member){
        Member member1=memberService.registerMember(member);
        return ResponseEntity.ok(member1);
    }
}
