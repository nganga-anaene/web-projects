package com.anaene.airlineserver.config.settings;

import com.anaene.airlineserver.data.entity.Client;
import com.anaene.airlineserver.data.entity.Passenger;
import com.anaene.airlineserver.data.entity.util.Name;
import com.anaene.airlineserver.data.repository.ClientRepository;
import com.anaene.airlineserver.web.service.PassengerService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class LoadClients {

    private final PassengerService passengerService;
    private final ClientRepository clientRepository;
    private final AppUtils appUtils;

    public LoadClients(PassengerService passengerService, ClientRepository clientRepository, AppUtils appUtils) {
        this.passengerService = passengerService;
        this.clientRepository = clientRepository;
        this.appUtils = appUtils;
    }

    public void addClients() {
        List<Passenger> passengers = passengerService.getAllPassengers();
        passengers.forEach(passenger -> {
            String username = setUsername(passenger.getPassport().getPerson().getName(), clientRepository).toLowerCase().trim();
            Name name = passenger.getPassport().getPerson().getName();
            String password = appUtils.passwordEncoder().encode("password");
            Client client = new Client(name, username, password, passenger);
            clientRepository.save(client);
        });
        Client client = clientRepository.findAll().stream().findFirst().get();
        System.out.println(client.getUsername() + " " + client.getPassword());
    }

    private String setUsername(Name name, ClientRepository clientRepository) {
        boolean usernameFound = true;
        String username = name.getFirstName() + name.getLastName();
        while (usernameFound) {
            try {
                clientRepository.findByUsername(username).orElseThrow();
                username = modifyUsername(username);
            } catch (Exception e) {
                usernameFound = false;
            }
        }
        return username;
    }

    private String modifyUsername(String username) {
        Random r = new Random();
        int limit = r.nextInt(3);
        StringBuilder builder = new StringBuilder();
        builder.append(username);
        for (int i = 0; i < limit; i++) {
            builder.append(r.nextInt(9));
        }
        return builder.toString();
    }
}
