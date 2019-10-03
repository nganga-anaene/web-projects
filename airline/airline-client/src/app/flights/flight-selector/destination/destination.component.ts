import {Component, Input, OnInit} from '@angular/core';
import {FlightResource} from '../../../resource-interfaces/flight-resource';
import * as moment from 'moment';
import {Moment} from 'moment';

@Component({
  selector: 'app-destination',
  templateUrl: './destination.component.html',
  styleUrls: ['./destination.component.scss']
})
export class DestinationComponent implements OnInit {

  @Input() flightResource: FlightResource;
  private arrivalTime: Moment;
  private departureTime: Moment;

  constructor() {
  }

  ngOnInit() {
    this.setTime();
  }

  private setTime() {
    this.departureTime = moment(this.flightResource.departureTime, 'YYYY-MM-DDTHH:mm:ss');
    this.arrivalTime = moment(this.flightResource.arrivalTime, 'YYYY-MM-DDTHH:mm:ss');
  }

  private getArrivalTime() {
    return this.arrivalTime.format('DD-MM-YYYY');
  }

  private getDepartureTime() {
    return this.departureTime.format('DD-MM-YYYY');
  }

  private getDepartureAirport() {
    return this.flightResource.departingAirport.name;
  }

  private getArrivalAirport() {
    return this.flightResource.arrivalAirport.name;
  }

  private getDuration() {
    return moment.utc(this.arrivalTime.diff(this.departureTime)).format('HH:mm');
  }

  private getPrice() {
    return this.flightResource.price;
  }
}
