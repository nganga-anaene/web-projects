package com.anaene.airlineserver.web.controller;

import com.anaene.airlineserver.web.controller.util.LoggedStatus;
import com.anaene.airlineserver.web.service.LoginService;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }


    @GetMapping("login")
    public ResponseEntity<Resource<LoggedStatus>> login() {
        try {
            return ResponseEntity.ok(loginService.loggedIn());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(loginService.notLoggedIn());
        }
    }

    @GetMapping("logout")
    public ResponseEntity<Resource<LoggedStatus>> logout() {
        return ResponseEntity.ok(loginService.loggedOut());
    }
}
