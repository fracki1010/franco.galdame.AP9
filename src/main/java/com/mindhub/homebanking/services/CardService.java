package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface CardService {


    ResponseEntity<Object> createdCard(CardType cardType, CardColor cardColor,
                                       Authentication authentication);

    int randomNumber(int min, int max);

    String creationNumberCard();
}