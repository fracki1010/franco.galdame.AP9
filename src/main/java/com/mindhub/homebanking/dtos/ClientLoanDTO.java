package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

public class ClientLoanDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private Long id;
    private String name;
    private int payments;
    private Double amount;
    private Loan loan_id;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.name = clientLoan.getLoan().getName();
        this.payments = clientLoan.getPayments();
        this.amount = clientLoan.getAmount();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPayments() {
        return payments;
    }

    public Double getAmount() {
        return amount;
    }
}
