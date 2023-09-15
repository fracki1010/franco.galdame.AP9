package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {


    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    TransactionRepository transactionRepository;


    //Test de loans
    @Test
    public void existLoans(){
        //Verifica que exista al menos un loan en la base de datos
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }
    @Test
    public void existPersonalLoan(){
        //Verifica que exista un loan personal en la base de datos
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }



    //Test de Clients
    @Test
    public void existClientAdmi(){
        //Verifica que exista un client con el rol admin en la base de datos
        List<String> clients = clientRepository
                                .findAll()
                                .stream()
                                .map(client -> client.getEmail())
                                .collect(Collectors.toList());
        assertThat(clients,hasItem(containsString("@admin.com")) );
    }

    @Test
    public void existClient(){
        //Verifica que exista al menos un client en la base de datos
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,is(not(empty())));
    }



    //Test de Card
    @Test
    public void existCards(){
        //Verifica que exista al menos una card en la base de datos
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,is(not(empty())));
    }
    @Test
    public void existGoldCard(){
        //Verifica que haya una tarjeta GOLD ya creada en la base de datos
        List<Card> cards = cardRepository.findAll();
        List<String> cardColor = cards.stream().map(card -> card.getColor().toString()).collect(Collectors.toList());
        assertThat(cardColor, hasItem(containsString("GOLD")));
    }



    //Test de Transaction
    @Test
    public void existTransaction(){
        //Verifica que exista al menos una transaction en la base de datos
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,is(not(empty())));
    }

    @Test
    public void existTransactionForLoan(){
        //Verifica que exista una transaction de un loan aprobado
        List<String> transactions = transactionRepository
                                    .findAll()
                                    .stream()
                                    .map(transaction -> transaction.getDescription())
                                    .collect(Collectors.toList());
        assertThat(transactions,hasItem(containsString("loan approved")));
    }



    //Test de account
    @Test
    public void existAccount(){
        //Verifica que exista al menos una cuenta en la base de datos
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,hasItem(is(not(empty()))));
    }
    @Test
    public void adminHasAccount(){
        //Verifica que si hay un admin este posea al menos una cuenta
        List<Account> accounts = accountRepository.findAll();
        List<String> accountAdmin = accounts.stream().map(account -> account.getClient().getEmail()).collect(Collectors.toList());
        assertThat(accountAdmin, hasItem(containsString("@admin.com")));
    }


}
