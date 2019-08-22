import {Resource} from 'angular4-hal';
import {Address} from './address';

export interface Airport extends Resource {
  id: number;
  name: string;
  address: Address;
}
