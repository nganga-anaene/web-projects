import {Resource} from 'angular4-hal';

export interface Booking extends Resource {
  id: number;
  flights: {};
  passenger: {};
  purchaseDate: string;
  purchasePrice: string;
  paymentCard: {};
}

export interface BookingsResource extends Resource {
  _embedded: {
    bookingList: Booking[];
  };
}
