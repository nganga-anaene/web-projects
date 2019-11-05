import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FlightService} from '../../services/flight.service';
import {FlightDetails} from '../flight-details';
import {Flight} from '../flight';

@Component({
  selector: 'app-single-flight',
  templateUrl: './single-flight.component.html',
  styleUrls: ['./single-flight.component.scss']
})
export class SingleFlightComponent implements OnInit {

  @Output() private singleFlightDetails = new EventEmitter<Flight>();

  constructor(private flightDetails: FlightDetails, private flightService: FlightService) {

  }

  ngOnInit() {
    this.flightService.getAirportsResource().subscribe(resource => {
      this.flightService.sendAirportSubscriptions(resource);
    });
  }

  setFlightDetails() {
    if (this.flightDetails.isSingleFlightValid()) {
      this.singleFlightDetails.emit(this.flightDetails.flightDetails);
    }
  }

  setDate($event: string) {
    this.flightDetails.setDepartureDate($event);
  }
}
