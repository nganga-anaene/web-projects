import {Resource} from 'angular4-hal';

export interface ShoppingCartResource extends Resource {
  id: number;
  bookItems: BookItem[];
}

export class ShoppingCart {
  id: number;
  bookItems: BookItem[];
}

export class BookItem {
  id: number;
  amount: number;
  price: number;
  total: number;
  book: {};
}

export class SimpleCart {
  id: number;
  bookItemIds: number[];
}
