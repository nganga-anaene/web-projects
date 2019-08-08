package com.anaene.airlineserver.web.service;

import com.anaene.airlineserver.data.entity.Airport;
import com.anaene.airlineserver.data.repository.AirportRepository;
import com.anaene.airlineserver.web.resource.AirportResourceAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AirportService {

    private final AirportRepository airportRepository;
    private final AirportResourceAssembler airportResourceAssembler;
    private PagedResourcesAssembler<Airport> airportPagedResourcesAssembler;

    public AirportService(AirportRepository airportRepository, AirportResourceAssembler airportResourceAssembler) {
        this.airportRepository = airportRepository;
        this.airportResourceAssembler = airportResourceAssembler;
    }

    public Page<Airport> getAirportsPage(int page, int size) {
        return airportRepository.findAll(PageRequest.of(page, size));
    }

    public Resource<Airport> getAirportResource(long id) {
        Airport airport = getAirport(id);
        Resource<Airport> resource = airportResourceAssembler.toResource(airport);
        return resource;
    }

    @Transactional
     Airport getAirport(long id) {
        return airportRepository.findById(id).orElseThrow();
    }
}
