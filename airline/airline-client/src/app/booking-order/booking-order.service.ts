import {Injectable} from '@angular/core';
import {FlightResource} from '../resource-interfaces/flight-resource';

@Injectable({
  providedIn: 'root'
})
export class BookingOrderService {

  private selectedForwardFlight: FlightResource;
  private selectedReturnFlight: FlightResource;

  constructor() {
  }

  setForwardFlight(forwardFlight: FlightResource) {
    this.selectedForwardFlight = forwardFlight;
  }

  get getForwardFlight() {
    return this.selectedForwardFlight;
  }

  get getReturnFlight() {
    return this.selectedReturnFlight;
  }

  setReturnFlight(flight: FlightResource) {
    this.selectedReturnFlight = flight;
  }

  resetForwardFlight() {
    this.selectedForwardFlight = null;
  }

  resetReturnFlight() {
    this.selectedReturnFlight = null;
  }

  isForwardFlightSelected() {
    return this.selectedForwardFlight != null;
  }

  isReturnFlightSelected() {
    return this.selectedReturnFlight != null;
  }
}
