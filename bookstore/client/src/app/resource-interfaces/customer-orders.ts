import {Resource} from 'angular4-hal';
import {BookItem} from './shopping-cart-resource';

export interface CustomerOrders extends Resource {
  _embedded: {
    customerOrderList: Order[];
  };
}

export interface Order {
  id: number;
  bookItems: BookItem[];
  orderStatus: string;
  shippingAddress: {};
  total: number;
}
