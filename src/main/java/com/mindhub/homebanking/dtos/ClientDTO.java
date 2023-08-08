package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
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


    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getName();
        this.lastname = client.getLastname();
        this.email = client.getEmail();
        this.accounts = client
                            .getAccounts()
                            .stream()
                            .map(account -> new AccountDTO(account))
                            .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
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
}