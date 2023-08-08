package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private Long id;
    private String firstName;
    private String lastname;
    private String email;

    //Propiedad nueva en mis cuentas

    @OneToMany(mappedBy = "clientId", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    public Client() {
    }

    public Client(String firstName, String lastname, String email) {
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return firstName;
    }

    public void setName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




    //Metodo para agregar una cuenta
    public void addAccount(Account account) {
        //Decirle a la mascota que el dueño soy yo

        account.setClientId(this);
        //agregar la mascota que me pasaron como parametro a mi coleccion de mascotas
        accounts.add(account);
    }
}

