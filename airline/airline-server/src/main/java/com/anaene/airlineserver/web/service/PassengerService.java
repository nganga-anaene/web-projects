package com.anaene.airlineserver.web.service;

import com.anaene.airlineserver.data.entity.Booking;
import com.anaene.airlineserver.data.entity.Passenger;
import com.anaene.airlineserver.data.entity.PaymentCard;
import com.anaene.airlineserver.data.repository.PassengerRepository;
import com.anaene.airlineserver.web.controller.PassengerController;
import com.anaene.airlineserver.web.resource.PassengerResourceAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private PagedResourcesAssembler<Passenger> passengerPagedResourcesAssembler;
    private PassengerResourceAssembler passengerResourceAssembler;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Transactional
    public Page<Passenger> passengerPage() {
        System.out.println(passengerRepository.findAll().get(0));
        return passengerRepository.findAll(PageRequest.of(0, 100));
    }

    @Transactional
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    @Transactional
    public void deletePassenger(Passenger passenger) {
        passengerRepository.delete(passenger);
    }

    @Transactional
    public PaymentCard getPaymentCard(Passenger passenger) {
        return passengerRepository.findById(passenger.getId()).get().getPaymentCards().stream().findFirst().get();
    }

    @Transactional
    public Passenger getPassengerById(long id) {
        return passengerRepository.findPassengerById(id).get();
    }


    public Passenger savePassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    @Transactional
    public void updateBooking(Passenger passenger, Booking booking) {
        passenger.addBooking(booking);
        passengerRepository.save(passenger);
    }

    public Resource<Passenger> getPassengerResource(Passenger passenger) {
        return passengerResourceAssembler.toResource(passenger);
    }

    public PagedResources<Resource<Passenger>> getPagedPassengerResources(Page<Passenger> passengerPage) {
        PagedResources<Resource<Passenger>> pagedResources = passengerPagedResourcesAssembler.toResource(passengerPage);
        pagedResources.add(linkTo(methodOn(PassengerController.class).getPassengersPage(passengerPage.getTotalPages(), passengerPage.getSize())).withRel("passengers"));
        return pagedResources;
    }

    public Page<Passenger> getPage(int page, int size) {
        return passengerRepository.findAll(PageRequest.of(page, size));
    }
}
