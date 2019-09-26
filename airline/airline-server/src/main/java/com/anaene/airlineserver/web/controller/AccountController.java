package com.anaene.airlineserver.web.controller;

import com.anaene.airlineserver.data.entity.Client;
import com.anaene.airlineserver.data.entity.Passenger;
import com.anaene.airlineserver.web.controller.util.RegistrationDetails;
import com.anaene.airlineserver.web.service.AccountService;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("register")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("")
    public ResponseEntity<Resource<Passenger>> addClient(@RequestBody RegistrationDetails registrationDetails) {
        try {
            return ResponseEntity.ok(accountService.addClient(registrationDetails));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
