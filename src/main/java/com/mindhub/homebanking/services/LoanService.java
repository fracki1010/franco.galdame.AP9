package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Set;


public interface LoanService {

    Set<LoanDTO> getLoans();

    ResponseEntity applyForLoan(LoanApplicationDTO loanApplicationDTO, Authentication authentication);

}
