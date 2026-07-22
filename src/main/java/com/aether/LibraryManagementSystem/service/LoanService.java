package com.aether.LibraryManagementSystem.service;

import com.aether.LibraryManagementSystem.dto.LoanRequest;
import com.aether.LibraryManagementSystem.dto.LoanResponseDto;
import com.aether.LibraryManagementSystem.dto.LoanReturnResponse;
import com.aether.LibraryManagementSystem.entities.Book;
import com.aether.LibraryManagementSystem.entities.BookCopy;
import com.aether.LibraryManagementSystem.entities.Loan;
import com.aether.LibraryManagementSystem.entities.Member;
import com.aether.LibraryManagementSystem.enums.CopyStatus;
import com.aether.LibraryManagementSystem.exception.BookCopyNotAvailableException;
import com.aether.LibraryManagementSystem.exception.LoanAlreadyReturnedException;
import com.aether.LibraryManagementSystem.exception.MemberNotEligibleException;
import com.aether.LibraryManagementSystem.exception.ResourceNotFoundException;
import com.aether.LibraryManagementSystem.repository.BookCopyRepository;
import com.aether.LibraryManagementSystem.repository.BookRepository;
import com.aether.LibraryManagementSystem.repository.LoanRepository;
import com.aether.LibraryManagementSystem.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.number.money.CurrencyUnitFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class LoanService {
    private final BookCopyRepository bookCopyRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final int FINE_PER_DAY= 15 ;

    @Transactional
    public LoanResponseDto issueLoan(LoanRequest loanRequest) {

        //validate Book
        Book book=bookRepository.findById(loanRequest.getBookId())
                .orElseThrow(()-> new ResourceNotFoundException("Book not found by id: "+loanRequest.getBookId()));
        BookCopy bookCopy;

     // validate member exists
        Member member=memberRepository.findById(loanRequest.getMemberId())
                    .orElseThrow(()->
                            new ResourceNotFoundException("Member is not found by id: "+loanRequest.getMemberId()));

        // check member's loan eligibility (active count + overdue check)
          List<Loan> loans= member.getLoans()
                            .stream()
                              .toList();
          // rule of eligibility if overDue >0 then no load will allow ,if active loan>3 then no loan
        if(isEligibleForLoan(loans)){
            // → find and validate an AVAILABLE book copy from bookId
           bookCopy= bookCopyRepository.findFirstByBookAndStatus(book,CopyStatus.AVAILABLE)
                    .orElseThrow(()->new BookCopyNotAvailableException("Copy of given book is not available"));


        }
        else throw new MemberNotEligibleException("Member is not eligible for loan");



        // → [transaction start]
           //create Loan (member, bookCopy, borrowDate=today, dueDate=+14d)
              Loan loan=new Loan();
              loan.setBookCopy(bookCopy);
              loan.setMember(member);
              loan.setBorrowDate(LocalDate.now());
              loan.setDueDate(LocalDate.now().plusDays(14));

        // update BookCopy status → BORROWED (protected by @Version)
             bookCopy.setStatus(CopyStatus.BORROWED);

             Loan saved= loanRepository.save(loan);
             bookCopyRepository.save(bookCopy);

        //→ [transaction commit — or full rollback if either write fails]
        // → build and return LoanResponse DTO
        LoanResponseDto responseDto=new LoanResponseDto();
        responseDto.setId(saved.getId());
        responseDto.setMemberName(member.getName());
        responseDto.setBookTitle(book.getTitle());
        responseDto.setDueDate(saved.getDueDate());
        responseDto.setBorrowDate(saved.getBorrowDate());

      return responseDto;


    }

    private boolean isEligibleForLoan(List<Loan> loans) {
        int activeLoans=0;

        for(Loan l: loans){
            if(l.getReturnDate()==null && LocalDate.now().isAfter(l.getDueDate()))
                return false;

            if(l.getReturnDate()==null){
                activeLoans++;
            }
            if(activeLoans >3)
                return false;
        }

        return true;
    }

    @Transactional
    public LoanReturnResponse returnLoan(Long id) {

        //find Loan by id, throw if missing

        Loan loan=loanRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("There is not any issued loan for id: "+id));

        //  → throw if already returned (returnDate != null)


        if(loan.getReturnDate()!=null){
            throw  new LoanAlreadyReturnedException("This loan was already returned on " + loan.getReturnDate());
        }
        //    → [transaction start]
          // set returnDate = today
       loan.setReturnDate(LocalDate.now());

//    → if bookCopy.status == BORROWED: set status = AVAILABLE
        BookCopy bookCopy=loan.getBookCopy();
        if(bookCopy.getStatus()==CopyStatus.BORROWED){
            bookCopy.setStatus(CopyStatus.AVAILABLE);
            bookCopyRepository.save(bookCopy);
        }
        loanRepository.save(loan);
//    → compute wasOverdue (and optionally: daysLate, fine) — not persisted, just derived for the response
        long daysLate = ChronoUnit.DAYS.between(loan.getDueDate(), loan.getReturnDate());
        BigDecimal fine = daysLate > 0 ?
                BigDecimal.valueOf(daysLate).multiply(BigDecimal.valueOf(FINE_PER_DAY)) :
                BigDecimal.ZERO;
//    → [transaction commit]
//  → build and return LoanResponseDto (include wasOverdue)

        LoanReturnResponse response=new LoanReturnResponse();

        response.setLoanId(loan.getId());
        response.setFine(fine);
        response.setReturnDate(loan.getReturnDate());

        return response;

    }
}
