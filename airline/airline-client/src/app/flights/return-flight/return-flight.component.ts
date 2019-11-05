import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FlightService} from '../../services/flight.service';
import {FlightDetails} from '../flight-details';
import {Flight} from '../flight';

@Component({
  selector: 'app-return-flight',
  templateUrl: './return-flight.component.html',
  styleUrls: ['./return-flight.component.scss']
})
export class ReturnFlightComponent implements OnInit {

  @Output() returnFlightDetails = new EventEmitter<Flight>();

  constructor(private flightDetails: FlightDetails, private flightService: FlightService) {
  }

  ngOnInit() {
    this.flightService.getAirportsResource().subscribe(resource => {
      this.flightService.sendAirportSubscriptions(resource);
    });
  }

  setReturnFlightDetails() {
    if (this.flightDetails.isReturnFlightValid()) {
      this.returnFlightDetails.emit(this.flightDetails.flightDetails);
    }
  }

  addReturnDate(date: string) {
    this.flightDetails.setReturnDate(date);
  }

  addForwardDate(date: string) {
    this.flightDetails.setDepartureDate(date);
  }
}

