package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("/loans")
    public Set<LoanDTO> getLoans(){
        return loanRepository
                    .findAll()
                    .stream()
                    .map(loan -> new LoanDTO(loan))
                    .collect(Collectors.toSet());
    }

    @RequestMapping(path = "/loans", method = RequestMethod.POST)
    public ResponseEntity applyForLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){

        //Cliente autenticado
        Client client = clientRepository.findByEmail(authentication.getName());

        //cuenta del cliente
        Account accountClient = client
                                .getAccounts()
                                .stream()
                                .filter(account -> account.getNumber().equals(loanApplicationDTO.getToAccountNumber()))
                                .findFirst()
                                .orElse(null);

        //Verifica que los campos no este vacios
        if (loanApplicationDTO.getToAccountNumber().isBlank() || loanApplicationDTO.getLoanId() == 0
        || loanApplicationDTO.getAmount() == 0 || loanApplicationDTO.getPayments() == 0){
            return new ResponseEntity<>("Completa todos los campos", HttpStatus.FORBIDDEN);
        }

        if (!loanRepository.existsById(loanApplicationDTO.getLoanId())){
            return new ResponseEntity<>("El prestamo no existe", HttpStatus.FORBIDDEN);
        }

        //Loan pedido por el cliente
        Loan loan = loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);
        //Verifica que el monto no exceda el prestamo maximo
        if (loan.getMaxAmount() <= loanApplicationDTO.getAmount()){
            return new ResponseEntity<>("El monto que pide excede a maximo posible", HttpStatus.FORBIDDEN);
        }

        //Verificar que la cantidad de cuotas exista en el prestamo
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("Selecciono una cantidad de cuotas inexistente", HttpStatus.FORBIDDEN);
        }

        if (!accountRepository.existsByNumber(loanApplicationDTO.getToAccountNumber())){
            return new ResponseEntity<>("La cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }

        //Verifica que la cuenta de destino pertenezca al cliente autenticado
        if (!client.getAccounts().contains(accountClient)){
            return new ResponseEntity<>("La cuenta de destino no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        }



        //Creacion del Prestamo al cliente
        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getPayments(),
                loanApplicationDTO.getAmount() * 1.2, client, loan);

        //Creacion de la transaccion
        Transaction transaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(),
                loan.getName()+" loan approved", LocalDate.now());

        //Asignacion del dinero a la cuenta
        accountClient.setBalance(accountClient.getBalance()+(loanApplicationDTO.getAmount()));

        //Asignacion de transaccion a la cuenta
        accountClient.addTransaction(transaction);

        //Guardado de cuenta, cliente y transaccion
        transactionRepository.save(transaction);
        accountRepository.save(accountClient);
        clientLoanRepository.save(clientLoan);

        return new ResponseEntity(HttpStatus.CREATED);
    }

}
