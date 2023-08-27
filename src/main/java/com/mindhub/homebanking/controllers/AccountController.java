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

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createdAccount(Authentication authentication){

        if (clientRepository.findByEmail(authentication.getName()).getAccounts().size() >= 3){
            return new ResponseEntity<>("Usted llego al limite de cuentas posibles",HttpStatus.FORBIDDEN);
        }

        //Creador de número random y comprobar que no exista en la base de datos
        String numberAccount;
        do {

            numberAccount = "VIN-" + String.valueOf(randomNumber(0, 99999999));

        }while (accountRepository.existsByNumber(numberAccount));


        //creacion de la cuenta unica
        Account accountCurrent = new Account(numberAccount,LocalDate.now(),0.0);

        //Identificaion del clinte y asignacion de cuenta a cliente
        clientRepository.findByEmail(authentication.getName()).addAccount(accountCurrent);

        //Guardado de cuenta
        accountRepository.save(accountCurrent);

        return new ResponseEntity<>("Cuenta creada con exito",HttpStatus.CREATED);
    }


    //Funcion del número aleatorio
    static int randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    //Identificacion del cliente
    public Client getCurrentClient(Authentication authentication){
        Client clientCurrent = clientRepository.findByEmail(authentication.getName());
        return clientCurrent;
    }
}
