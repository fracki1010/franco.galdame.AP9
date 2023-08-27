package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class HomebankingApplication<commandLineRunner> {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return (arg) -> {
			//Creacion de clientes
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("1234"));
			Client client2 = new Client("Franco", "Galdame", "franco23@admin.com",passwordEncoder.encode("123"));

			clientRepository.save(client1);
			clientRepository.save(client2);


			//Creacion de cuentas
			Account account1 = new Account("VIN001", LocalDate.now(),5000.0);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500.0 );

			Account account3 = new Account("VIN003", LocalDate.now().plusDays(2), 2000.0 );
			Account account4 = new Account("VIN004", LocalDate.now().plusDays(1), 10500.0 );



			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);
			client2.addAccount(account4);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);


			//Creacion de transacciones

			Transaction transaction1 = new Transaction(TransactionType.CREDIT,400,"Progresar",LocalDate.now());
			Transaction transaction2 = new Transaction(TransactionType.DEBIT, -300, "Pago x", LocalDate.now().plusDays(4));
			Transaction transaction3 = new Transaction(TransactionType.DEBIT, -1000, "Pago x", LocalDate.now().plusDays(3));
			Transaction transaction4 = new Transaction(TransactionType.CREDIT, 1500, "Sueldo", LocalDate.now().plusDays(1));


			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account2.addTransaction(transaction3);
			account4.addTransaction(transaction4);


			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);


			//Creacion de prestamos

			Loan loan1 = new Loan("Hipotecario",500000,List.of(12,24,36,48,60));
			Loan loan2 = new Loan("Personal",100000, List.of(6,12,24));
			Loan loan3 = new Loan("Automotriz",300000,List.of(12,24,36));

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);



			//Agregación de prestamos a clientes

			ClientLoan clientLoan1 = new ClientLoan(60,400000.0, client1,loan1);
			ClientLoan clientLoan2 = new ClientLoan(12,12000.0, client1,loan2);


			ClientLoan clientLoan3 = new ClientLoan(24,100000.0, client2,loan2);
			ClientLoan clientLoan4 = new ClientLoan(36,200000.0, client2,loan3);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);


			//Creacion de prestamo

			Card card1 = new Card(client1.getFirstName()+" "+client1.getLastname(),CardType.DEBIT,CardColor.GOLD,"5090340500","998",LocalDate.now(),LocalDate.now().plusYears(5));
			Card card2 = new Card(client1.getFirstName()+" "+client1.getLastname(),CardType.CREDIT,CardColor.TITANIUM,"5085909058","889",LocalDate.now(),LocalDate.now().plusYears(5));
			Card card4 = new Card(client1.getFirstName()+" "+client1.getLastname(),CardType.CREDIT,CardColor.GOLD,"5085909068","839",LocalDate.now(),LocalDate.now().plusYears(5));

			Card card3 = new Card(client2.getFirstName()+" "+client2.getLastname(),CardType.CREDIT,CardColor.SILVER,"5040563020","337",LocalDate.now(),LocalDate.now().plusYears(6));

			client1.addCard(card1);
			client1.addCard(card2);
			client2.addCard(card3);
			client1.addCard(card4);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
			cardRepository.save(card4);

		};
	}
}
