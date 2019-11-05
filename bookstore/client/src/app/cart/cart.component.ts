import {Component, OnInit} from '@angular/core';
import {CartService} from '../cart.service';
import {Observable, of, Subject} from 'rxjs';
import {BookItem} from '../resource-interfaces/shopping-cart-resource';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  private cartItems = new Subject<BookItem[]>();

  constructor(private cartService: CartService) {
  }

  ngOnInit() {
    if (this.cartService.hasCartItems()) {
      this.cartService.getShoppingCart().subscribe(value => this.cartItems.next(value.bookItems));
    } else {

    }
  }

  deleteBookItem(id: number) {
    this.cartService.deleteCartItem(id).subscribe(value => {
      this.cartService.getShoppingCart().subscribe(value1 => this.cartItems.next(value1.bookItems));
    });
  }

  getTotal(items: BookItem[]): Observable<number> {
    if (items.length > 0) {
      return of(items.map(item => item.total).reduce((a, b) => a + b));
    } else {
      return of(0);
    }
  }

  hasItems(items: any) {
    return items.length > 0;
  }
}
