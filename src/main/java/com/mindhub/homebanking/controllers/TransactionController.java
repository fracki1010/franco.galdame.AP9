package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@Transactional
@RestController
@RequestMapping("/api")
public class TransactionController {

@Autowired
private TransactionService transactionService;


    @RequestMapping(path = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> createdTransaction(
            @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,
            @RequestParam Double amount, @RequestParam String description,
            Authentication authentication){
        return transactionService.createdTransaction(fromAccountNumber, toAccountNumber, amount,
                description, authentication);
    }

}
