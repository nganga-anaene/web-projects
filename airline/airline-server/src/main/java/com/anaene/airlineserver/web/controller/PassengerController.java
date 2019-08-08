package com.anaene.airlineserver.web.controller;

import com.anaene.airlineserver.data.entity.Passenger;
import com.anaene.airlineserver.web.service.PassengerService;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("passengers")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping(value = "", params = {"page", "size"})
    public ResponseEntity<PagedResources<Resource<Passenger>>> getPassengersPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<Passenger> passengerPage = passengerService.getPage(page, size);
        return ResponseEntity.ok(passengerService.getPagedPassengerResources(passengerPage));
    }
}
