package com.anaene.airlineserver.web.service;

import com.anaene.airlineserver.web.controller.LoginController;
import com.anaene.airlineserver.web.controller.PassengerController;
import com.anaene.airlineserver.web.controller.util.LoggedStatus;
import org.springframework.hateoas.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class LoginService {


    private final AccountService accountService;

    public LoginService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Transactional
    public Resource<LoggedStatus> loggedIn() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long passengerId = accountService.getPassengerId(user.getUsername());
        Resource<LoggedStatus> resource = new Resource<>(LoggedStatus.LOGGED_IN);
        resource.add(linkTo(methodOn(PassengerController.class).getPassengerDetails(passengerId)).withRel("account-details"));
        resource.add(linkTo(methodOn(LoginController.class).logout()).withRel("logout"));
        return resource;
    }

    public Resource<LoggedStatus> notLoggedIn() {
        return loggedOut();
    }

    public Resource<LoggedStatus> loggedOut() {
        Resource<LoggedStatus> resource = new Resource<>(LoggedStatus.LOGGED_OUT);
        resource.add(linkTo(methodOn(LoginController.class).login()).withRel("login"));
        return resource;
    }
}
