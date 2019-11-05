import {Resource} from 'angular4-hal';
import {FlightResource} from './flight-resource';

export interface FlightsResource extends Resource {
  _embedded: {
    flightList: FlightResource[];
  };
}
