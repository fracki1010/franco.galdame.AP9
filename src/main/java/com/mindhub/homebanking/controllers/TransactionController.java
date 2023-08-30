package com.mindhub.homebanking.controllers;

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




    @RequestMapping(path = "/transaction", method = RequestMethod.POST)
    public ResponseEntity<Object> createdTransaction(
            @RequestParam Double monto, @RequestParam Long description,
            @RequestParam String originNumber, @RequestParam String destinationNumber,
            @RequestParam Authentication authentication){


        return
    }

}
