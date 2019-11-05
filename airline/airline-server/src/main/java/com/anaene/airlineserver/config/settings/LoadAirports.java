package com.anaene.airlineserver.config.settings;

import com.anaene.airlineserver.data.entity.Address;
import com.anaene.airlineserver.data.entity.Airport;
import com.anaene.airlineserver.data.repository.AddressRepository;
import com.anaene.airlineserver.data.repository.AirportRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LoadAirports {

    private final AirportRepository airportRepository;
    private final AddressRepository addressRepository;
    private List<AirportInfo> airportInfoList = Arrays.asList(
            new AirportInfo("Heathrow", "London", "England"),
            new AirportInfo("Hartsfield-Jackson", "Atlanta", "USA"),
            new AirportInfo("Beijing Capital Airport", "Beijing", "China"),
            new AirportInfo("Dubai International Airport", "Dubai", "UAE"),
            new AirportInfo("LAX", "Los Angeles", "USA"),
            new AirportInfo("Haneda", "Tokyo", "Japan"),
            new AirportInfo("O'Hare", "Chicago", "USA"),
            new AirportInfo("Hong Kong International Airport", "Hong Kong", "Hong Kong"),
            new AirportInfo("Frankfurt Airport", "Frankfurt", "Germany"),
            new AirportInfo("Singapore Changi", "Singapore", "Singapore")
    );

    public LoadAirports(AirportRepository airportRepository, AddressRepository addressRepository) {
        this.airportRepository = airportRepository;
        this.addressRepository = addressRepository;
    }

    public void addAirports() {
        airportInfoList.forEach(airportInfo -> {
            Address address = addressRepository.save(new Address("some street", airportInfo.getCity(), "some postcode", airportInfo.getCountry()));
            Airport airport = new Airport(airportInfo.getName(), address);
            airportRepository.save(airport);
        });
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class AirportInfo {

        private String name;
        private String city;
        private String country;
    }

}
