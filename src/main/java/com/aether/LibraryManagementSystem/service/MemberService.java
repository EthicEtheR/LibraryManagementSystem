package com.aether.LibraryManagementSystem.service;

import com.aether.LibraryManagementSystem.entities.Member;
import com.aether.LibraryManagementSystem.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member registerMember(Member member){
        return memberRepository.save(member);
    }
}
