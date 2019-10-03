import {Component, OnInit} from '@angular/core';
import {FlightService} from '../services/flight.service';
import {Flight} from './flight';
import {AirportResource} from '../resource-interfaces/airport-resource';

@Component({
  selector: 'app-flights',
  templateUrl: './flights.component.html',
  styleUrls: ['./flights.component.scss']
})
export class FlightsComponent implements OnInit {

  private airports: AirportResource[];

  constructor(private flightService: FlightService) {
  }

  ngOnInit() {
    this.flightService.getAirportResources().subscribe(value => {
      this.airports = value;
    });
  }

  processSingleFlights(flight: Flight) {
    try {
      this.getFlightResources(flight.DepartureAirportName, flight.ArrivalAirportName, flight.DepartureDate)
        .subscribe(resource => {
          this.flightService.sendForwardFlightSubscriptions(resource);
          this.flightService.sendEmptyReturnFlights();
        });
    } catch (e) {
    }
  }

  getAirportId(airportName): number {
    return this.airports.filter(value => value.name === airportName).map(value => value.id)[0];
  }

  processReturnFlights(flight: Flight) {
    try {
      this.getFlightResources(flight.DepartureAirportName, flight.ArrivalAirportName, flight.DepartureDate)
        .subscribe(resource => this.flightService.sendForwardFlightSubscriptions(resource));
      this.getFlightResources(flight.ArrivalAirportName, flight.DepartureAirportName, flight.ReturnDate)
        .subscribe(resource => this.flightService.sendReturnFlightSubscriptions(resource));
    } catch (e) {
    }
  }

  getFlightResources(departureAirportName, arrivalAirportName, departureDate) {
    const departureAirportId = this.getAirportId(departureAirportName);
    const arrivalAirportId = this.getAirportId(arrivalAirportName);
    return this.flightService.getFlights(departureAirportId, arrivalAirportId, departureDate);
  }
}
