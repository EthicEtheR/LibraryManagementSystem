package com.aether.LibraryManagementSystem.controller;

import com.aether.LibraryManagementSystem.entities.Member;
import com.aether.LibraryManagementSystem.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<List<Member>> registerMember(@RequestBody List<Member> member){
        List<Member> member1=memberService.registerMember(member);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(member1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberBYId(@PathVariable Long id ){
        Member member=memberService.getById(id);
        return ResponseEntity.ok(member);
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAll(){
        List<Member> members=memberService.getAll();

        return ResponseEntity.ok(members);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMemberDetails(@PathVariable Long id,
                                                      @RequestBody Member member){
        Member updatedMember=memberService.updateMember(id,member);

        return ResponseEntity.ok(updatedMember);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        memberService.deleteBYId(id);
        return ResponseEntity.ok("Deleted "+id);
    }
}
