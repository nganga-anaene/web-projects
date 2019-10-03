import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LoginService} from '../login/login.service';
import {CookieService} from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: HttpClient, private cookieService: CookieService) {
  }

  getAccountDetails() {
    return this.http.get('http://localhost:8080/account', {
      headers:
        LoginService.setHttpHeaders(this.cookieService.get('username'), this.cookieService.get('password'))
    });
  }

  getOrders(ordersUrl: string) {
    return this.http.get(ordersUrl, {
      headers: LoginService.setHttpHeaders(this.cookieService.get('username'), this.cookieService.get('password'))
    });
  }

  getAddresses(addressUrl: string) {
    return this.http.get(addressUrl, {
      headers: LoginService.setHttpHeaders(this.cookieService.get('username'), this.cookieService.get('password'))
    });
  }
}
