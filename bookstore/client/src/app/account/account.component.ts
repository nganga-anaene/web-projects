import {Component, OnInit} from '@angular/core';
import {AccountService} from './account.service';
import {CustomerOrders, Order} from '../resource-interfaces/customer-orders';
import {Resource} from 'angular4-hal';
import {Subject} from 'rxjs';
import {AddressResource, AddressResources} from '../resource-interfaces/address-resource';
import {BookItem} from '../resource-interfaces/shopping-cart-resource';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {

  private orders = new Subject<Order[]>();
  private addresses = new Subject<AddressResource[]>();

  constructor(private accountService: AccountService) {
  }

  ngOnInit() {
    this.accountService.getAccountDetails().subscribe((value: Resource) => {
      this.accountService.getOrders(value._links.orders.href).subscribe((orders: CustomerOrders) => {
        this.orders.next(orders._embedded.customerOrderList);
      });
      this.accountService.getAddresses(value._links.addresses.href).subscribe((addresses: AddressResources) => {
        this.addresses.next(addresses._embedded.addressList);
      });
    });
  }

  checkItems(customerOrders: any[]): boolean {
    if (customerOrders != null) {
      return customerOrders.length > 0;
    } else {
      return false;
    }
  }

  getTotal(bookItems: BookItem[]) {
    if (bookItems.length > 0) {
      return bookItems.map(item => item.total).reduce((a, b) => a + b);
    } else if (bookItems.length === 1) {
      return bookItems[0].total;
    } else {
      return 0;
    }
  }

  checkOrderStatus(orderStatus: string) {
    return orderStatus.toLowerCase() === 'completed';
  }

  cancelOrder(id) {
    console.log(id);
  }
}
