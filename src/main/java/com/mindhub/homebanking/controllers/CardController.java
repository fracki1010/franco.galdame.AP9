package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired


    private ClientRepository clientRepository;

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createdCard(
            @RequestParam CardType cardType, @RequestParam CardColor cardColor,
            Authentication authentication) {

    String colorOrTypeCard = "";

        //Tarjetas de diferente tipo repetidas
        if (amountCardType(cardType, authentication) == 3){
            switch (cardType){
                case CREDIT:
                    colorOrTypeCard = "CREDIT";
                    break;
                case DEBIT:
                    colorOrTypeCard = "DEBIT";
                    break;
            }
            return new ResponseEntity<>("Alcanzo el limite de tarjetas de "+colorOrTypeCard, HttpStatus.FORBIDDEN);
        }


        //Tarjetas del mismo color repetidas
        if (amountCardColor(cardType, cardColor, authentication) == 1){
            switch (cardColor){
                case GOLD:
                    colorOrTypeCard = "GOLD";
                    break;
                case SILVER:
                    colorOrTypeCard = "SILVER";
                    break;
                case TITANIUM:
                    colorOrTypeCard = "TITANIUM";
                    break;
            }
            return new ResponseEntity<>("Alcanzo el limite de la tarjeta "+colorOrTypeCard, HttpStatus.FORBIDDEN);
        }


        //Creacion de cvv aleatorio
        String cvv;
        do {
            cvv = String.valueOf(randomNumber(100,999));
        }while (cardRepository.existsByCvv(cvv));


        //Creacion del número de tarjera aleatorio
        String numberCard;
        do {

            numberCard = creationNumberCard();

        }while (cardRepository.existsByNumber(numberCard));


        //Creacion de tarjeta
        Client client = clientRepository.findByEmail(authentication.getName());
        Card card = new Card(client.getFirstName() +" "+ client.getLastname(),
                                cardType,
                                cardColor,
                                numberCard,
                                cvv,
                                LocalDate.now(),
                                LocalDate.now().plusYears(5));

        //Asignacion de tarjeta a cliente
        clientRepository.findByEmail(authentication.getName()).addCard(card);

        //Guardado de tarjera
        cardRepository.save(card);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //Funcion del número aleatorio
    static int randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    static String creationNumberCard(){
        String numbersCardsComplete = "";
        for (int i=0;i<4;i++){
            String numbersCard = String.valueOf(randomNumber(1000, 9999));
            numbersCardsComplete += numbersCard;
            if (i<3){
                numbersCardsComplete += "-";
            }
        }
        return numbersCardsComplete;
    }

    public int amountCardType(CardType cardType, Authentication authentication){
        return clientRepository
                .findByEmail(authentication.getName())
                .getCards()
                .stream()
                .filter(card -> card.getType() == cardType)
                .collect(Collectors.toSet())
                .size();
    }
    public int amountCardColor(CardType cardType, CardColor cardColor, Authentication authentication){
        Set<Card> cardsCurrent = clientRepository
                .findByEmail(authentication.getName())
                .getCards()
                .stream()
                .filter(card -> card.getType() == cardType)
                .collect(Collectors.toSet());
        int amountcard = cardsCurrent
                .stream()
                .filter(card -> card.getColor().equals(cardColor))
                .collect(Collectors.toList())
                .size();
        return amountcard;
    }


}
