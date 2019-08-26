package com.anaene.airlineserver.web.service;

import com.anaene.airlineserver.data.entity.Client;
import com.anaene.airlineserver.data.entity.Passenger;
import com.anaene.airlineserver.data.repository.ClientRepository;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AccountService {

    private final ClientRepository clientRepository;
    private final PassengerService passengerService;

    public AccountService(ClientRepository clientRepository, PassengerService passengerService) {
        this.clientRepository = clientRepository;
        this.passengerService = passengerService;
    }

    @Transactional
    public Resource<Passenger> addClient(Client client) {
        Passenger passenger = clientRepository.save(client).getPassenger();
        return passengerService.getPassengerResource(passenger.getId());
    }

    @Transactional
    public long getPassengerId(String username) {
        return clientRepository.findByUsername(username).orElseThrow().getPassenger().getId();
    }
}