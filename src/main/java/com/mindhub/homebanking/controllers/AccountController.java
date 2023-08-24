package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;


    @GetMapping("/accounts")
    public List<AccountDTO> getAccount(){
        List<Account> allAccounts = accountRepository.findAll();

        return allAccounts
                    .stream()
                    .map(currentAccount -> new AccountDTO(currentAccount))
                    .collect(Collectors.toList());
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccountById(@PathVariable Long id){
        Optional<Account> accountOptional = accountRepository.findById(id);
        return new AccountDTO (accountOptional.get());
    }

    @GetMapping("/clients/current/accounts")
    public Account CreatedAccountCurrentClient(Authentication authentication){
        Client clientCurrent = clientRepository.findByEmail(authentication.getName());

        return new Client(clientCurrent);
    }


    public ResponseEntity<Object> createdAccount(
            @RequestParam String number, @RequestParam LocalDate creationDate,
            @RequestParam Double balance){

        number = "VIN-"+  String.valueOf(randomNumber(0,99999999));
        //Se debe verificar que el numero de cuenta no se repita
        /*if(clientRepository.findByEmail(email) !=null){
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }*/
        accountRepository.save(new Account(number,)
        clientRepository.save(new Client(firstName,lastName,email,passwordEncoder.encode(password)));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    //Funcion del número aleatorio
    static int randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
