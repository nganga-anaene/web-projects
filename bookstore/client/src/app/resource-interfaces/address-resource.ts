import {Resource} from 'angular4-hal';

export interface AddressResources extends Resource {
  _embedded: {
    addressList: AddressResource[];
  };
}

export interface AddressResource extends Resource {
  id: number;
  street: string;
  city: string;
  country: string;
  postcode: string;
}
