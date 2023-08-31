package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getClients();

    ClientDTO getClientById(Long id);

    ResponseEntity<Object> register(String firstName, String lastName, String email,
                                    String password, Authentication authentication);

    ClientDTO getCurrentClient(Authentication authentication);

    int randomNumber(int min, int max);
}
