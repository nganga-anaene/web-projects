import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LoginService} from '../login/login.service';
import {CookieService} from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private ordersUrl = 'http://localhost:8080/account/orders/';
  private addressUrl = 'http://localhost:8080/account/addresses/';

  constructor(private http: HttpClient, private cookieService: CookieService) {
  }

  getAccountDetails() {
    return this.http.get('http://localhost:8080/account', {
      headers:
        LoginService.setHttpHeaders(this.cookieService.get('username'), this.cookieService.get('password'))
    });
  }

  getOrders() {
    return this.http.get(this.ordersUrl, {
      headers: LoginService.setHttpHeaders(this.cookieService.get('username'), this.cookieService.get('password'))
    });
  }

  getAddresses() {
    return this.http.get(this.addressUrl, {
      headers: LoginService.setHttpHeaders(this.cookieService.get('username'), this.cookieService.get('password'))
    });
  }

  getOrder(id: string) {
    return this.http.get(this.ordersUrl + id,
      {headers: LoginService.setHttpHeaders(this.cookieService.get('username'), this.cookieService.get('password'))});
  }
}
