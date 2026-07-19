package com.aether.LibraryManagementSystem.service;

import com.aether.LibraryManagementSystem.entities.Member;
import com.aether.LibraryManagementSystem.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.MissingResourceException;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> registerMember(List<Member> member){

        //TODO before returning first  we have to use model mapper to change result in dtos
        return memberRepository.saveAll(member);
    }

    public Member getById(Long id) {

        //TODO before returning first  we have to use model mapper to change result in dtos

        return memberRepository.getReferenceById(id);
    }

    public List<Member> getAll() {

        //TODO before returning first  we have to use model mapper to change result in dtos

        return memberRepository.findAll();
    }

    public void deleteBYId(Long id) {
        if(memberRepository.existsById(id))
           memberRepository.deleteById(id);

        throw new RuntimeException("Member not found by this id "+id);
    }

    public Member updateMember(Long id, Member member) {
        Member updatedMember=memberRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Member not found , Enter Valid id"));

        updatedMember.setName(member.getName());
        updatedMember.setEmail(member.getEmail());
        updatedMember.setMembershipDate(member.getMembershipDate());

       return  memberRepository.save(updatedMember);

    }
}
