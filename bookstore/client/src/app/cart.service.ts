import {Injectable} from '@angular/core';
import {CookieService} from 'ngx-cookie-service';
import {HttpClient} from '@angular/common/http';
import {ShoppingCartResource, SimpleCart} from './resource-interfaces/shopping-cart-resource';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cartName = 'cart';
  private cartUrl = 'http://localhost:8080/shopping-cart/';
  private createUrl = this.cartUrl + 'create';

  constructor(private cookieService: CookieService, private http: HttpClient) {
  }

  createCart() {
    return this.http.get<ShoppingCartResource>(this.createUrl);
  }

  setCart() {
    if (!this.cookieService.check(this.cartName)) {
      this.createCart().subscribe(cart => {
        this.cookieService.set(this.cartName, '' + cart.id);
      });
    }
  }
  getShoppingCart() {
    return this.http.get<ShoppingCartResource>(this.cartUrl + this.cookieService.get(this.cartName));
  }

  deleteCart() {
    this.http.delete(this.cartUrl + this.cookieService.get(this.cartName));
  }

  hasCartItems() {
    return this.cookieService.check(this.cartName);
  }

  updateShoppingCart(cart: SimpleCart) {
    return this.http.post(this.cartUrl + cart.id, cart);
  }

  getSimpleCart(cart: ShoppingCartResource) {
    const simpleCart = new SimpleCart();
    simpleCart.id = cart.id;
    simpleCart.bookItemIds = cart.bookItems.map(item => item.id);
    return simpleCart;
  }

  deleteCartItem(id: number) {
    return this.http.delete(this.cartUrl + this.cookieService.get(this.cartName) + '/book-item/' + id);
  }
}
