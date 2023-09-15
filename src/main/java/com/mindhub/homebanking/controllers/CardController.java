package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;


    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createdCard(
            @RequestParam CardType cardType, @RequestParam CardColor cardColor,
            Authentication authentication){
        return cardService.createdCard(cardType, cardColor, authentication);
    }

}