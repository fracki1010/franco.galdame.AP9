package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.time.LocalDate;

public class AccountDTO {

    private String number;
    private LocalDate creationDate;
    private Double balance;

    public AccountDTO(Account account) {
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getDate() {
        return creationDate;
    }

    public Double getBalance() {
        return balance;
    }
}
