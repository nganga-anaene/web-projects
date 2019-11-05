import {Resource} from 'angular4-hal';
import {AirportResource} from './airport-resource';

export interface AirportsResource extends Resource {
  _embedded: {
    airportList: AirportResource[];
  };
}
