import {Component, Input, OnInit} from '@angular/core';
import {FlightService} from '../../services/flight.service';
import {FlightDetails} from '../flight-details';

@Component({
  selector: 'app-airports',
  templateUrl: './airports.component.html',
  styleUrls: ['./airports.component.scss']
})
export class AirportsComponent implements OnInit {

  @Input() private flightDetails: FlightDetails;

  constructor() {
  }

  ngOnInit() {
  }

  setArrivalAirport($event: string) {
    this.flightDetails.setArrivalAirportName($event);
  }

  setDepartureAirport($event: string) {
    this.flightDetails.setDepartureAirportName($event);
  }
}
