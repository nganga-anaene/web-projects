import {Resource} from 'angular4-hal';

export interface BookItemsResource extends Resource {
  _embedded: {
    bookItemList: BookItemResource[];
  };
}

export interface BookItemResource extends Resource {
  id: number;
  book: {};
  price: number;
  total: number;
  amount: number;
}
