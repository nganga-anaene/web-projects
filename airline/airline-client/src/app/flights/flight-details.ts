import {Injectable} from '@angular/core';
import {Flight} from './flight';

@Injectable({
  providedIn: 'root'
})
export class FlightDetails {

  private flight = new Flight();

  constructor() {
  }

  setDepartureDate(date) {
    this.flight.setDepartureDate(date);
  }

  setArrivalAirportName(airportName) {
    this.flight.setArrivalAirportName(airportName);
  }

  setDepartureAirportName(airportName) {
    this.flight.setDepartureAirportName(airportName);
  }

  setReturnDate(date) {
    this.flight.setReturnDate(date);
  }

  isSingleFlightValid() {
    return this.flight.isSingleFlightValid();
  }

  isReturnFlightValid() {
    return this.flight.isReturnFlightValid();
  }

  get flightDetails() {
    return this.flight;
  }
}
