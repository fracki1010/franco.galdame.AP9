package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastname;
    private String email;
    private Set<AccountDTO> accounts = new HashSet<>();
    private Set<ClientLoanDTO> loans = new HashSet<>();
    private Set<CardDTO> cards = new HashSet<>();


    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastname = client.getLastname();
        this.email = client.getEmail();
        this.accounts = client
                            .getAccounts()
                            .stream()
                            .map(account -> new AccountDTO(account))
                            .collect(Collectors.toSet());
        this.loans = client
                        .getClientLoans()
                        .stream()
                        .map(loan -> new ClientLoanDTO(loan))
                        .collect(Collectors.toSet());
        this.cards = client
                        .getCards()
                        .stream()
                        .map(card -> new CardDTO(card))
                        .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}