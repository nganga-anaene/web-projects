import {Resource} from 'angular4-hal';
import {AirportResource} from './airport-resource';

export interface FlightResource extends Resource {
  id: number;
  departingAirport: AirportResource;
  arrivalAirport: AirportResource;
  departureTime: string;
  arrivalTime: string;
  price: number;
  maxBookings: number;
  currentBookings: number;
}
