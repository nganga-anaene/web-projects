package com.anaene.airlineserver.web.controller;

import com.anaene.airlineserver.web.service.ClientService;
import com.anaene.airlineserver.web.service.PassengerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    private final PassengerService passengerService;
    private final ClientService clientService;

    public LoginController(PassengerService passengerService, ClientService clientService) {
        this.passengerService = passengerService;
        this.clientService = clientService;
    }

    @PostMapping("")
    public ResponseEntity login() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity.ok(passengerService.getPassengerResource(clientService.getPassengerId(user.getUsername())));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to login");
        }
    }
}
