package com.mindhub.homebanking.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CardUtilsTest {

    //Creacion del número
    @Test
    public void cardNumberIsCreated(){
        //Verifica que el numero no se null o este vacio
        String cardNumber = CardUtils.creationNumberCard();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }
    @Test
    void createDifferentAccount(){
        //Verifica que se creen numeros diferentes
        String cardNumerCurrent = CardUtils.creationNumberCard();
        String cardNew = CardUtils.creationNumberCard();
        assertThat(cardNumerCurrent, not(equalTo(cardNew)));
    }


    //Creacion de un numero random
    @Test
    void getCvv() {
        //Verifica que el numero no sea null o este vacio
        String cvv = CardUtils.getCvv();
        assertThat(cvv, is(not(emptyOrNullString())));
    }

    @Test
    void limitOfRandomNumber() {
        //Verificar cuantas veces aparece el limite en los numero generados
        //Si es mas de 10 no pasa el test
        List<String> randomNumber = new ArrayList<>();
        do {
            randomNumber.add(CardUtils.getCvv());
            if(randomNumber.contains("123")){
                int var = randomNumber.size();
            }
        } while(randomNumber.size()<10000);
        List<String> limitNumber = randomNumber
                                    .stream()
                                    .filter(limit -> limit.equals("999") || limit.equals("0"))
                                    .collect(Collectors.toList());
        assertThat(limitNumber, hasSize(not(greaterThan(10))));
    }
}