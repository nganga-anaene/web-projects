import {Component, OnInit} from '@angular/core';
import {FlightService} from '../../services/flight.service';
import {FlightResource} from '../../resource-interfaces/flight-resource';
import {BookingOrderService} from '../../booking-order/booking-order.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-flight-selector',
  templateUrl: './flight-selector.component.html',
  styleUrls: ['./flight-selector.component.scss']
})
export class FlightSelectorComponent implements OnInit {

  private forwardFlights: FlightResource[] = [];
  private returnFlights: FlightResource[] = [];
  private hasReturnFlight: boolean;

  constructor(private flightService: FlightService, private bookingService: BookingOrderService, private router: Router) {
  }

  ngOnInit() {
    this.setForwardFlights();
    this.setReturnFlights();
  }

  private setForwardFlights() {
    this.flightService.getForwardFlightResources().subscribe(value => {
      this.bookingService.resetForwardFlight();
      this.forwardFlights = value;
    });
  }

  private setReturnFlights() {
    this.flightService.getReturnFlightResources().subscribe(value => {
      this.bookingService.resetReturnFlight();
      if (value != null) {
        this.returnFlights = value;
      } else {
        this.returnFlights = [];
      }
      this.hasReturnFlight = this.wantsReturnFlight();
    });
  }

  private selectedFlightsText() {
    if (this.wantsReturnFlight()) {
      return 'Selected Flights';
    } else {
      return 'Selected Flight';
    }
  }

  private wantsReturnFlight() {
    return this.returnFlights.length > 0;
  }

  private flightsFound(): boolean {
    return this.forwardFlights.length > 0;
  }

  hasChosenForwardFlight(): boolean {
    return this.bookingService.isForwardFlightSelected();
  }

  chooseForwardFlight(flight: FlightResource) {
    this.bookingService.setForwardFlight(flight);
  }

  hasChosenReturnFlight() {
    return this.bookingService.isReturnFlightSelected();
  }

  chooseReturnFlight(flight: FlightResource) {
    this.bookingService.setReturnFlight(flight);
  }

  getForwardFlight() {
    return this.bookingService.getForwardFlight;
  }

  getReturnFlight() {
    return this.bookingService.getReturnFlight;
  }

  getPrice() {
    if (this.getForwardFlight() != null && this.getReturnFlight() != null) {
      return this.getForwardFlight().price + this.getReturnFlight().price;
    } else if (this.getForwardFlight() != null) {
      return this.getForwardFlight().price;
    } else if (this.getReturnFlight() != null) {
      return this.getReturnFlight().price;
    }
  }


  hasSelectedFlights() {
    if (this.hasReturnFlight) {
      return this.hasChosenReturnFlight() && this.hasChosenForwardFlight();
    } else {
      return this.hasChosenForwardFlight;
    }
  }

  processBooking() {
    this.router.navigateByUrl('flights/booking');
  }
}
