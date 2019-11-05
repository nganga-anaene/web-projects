import {Resource} from 'angular4-hal';
import {AddressResource} from './address-resource';

export interface AirportResource extends Resource {
  id: number;
  name: string;
  address: AddressResource;
}
