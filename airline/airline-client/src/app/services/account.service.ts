import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LoginService} from './login.service';
import {Subject} from 'rxjs';
import {Booking, BookingsResource} from '../resource-interfaces/bookings-resource';
import {AccountResource} from '../account/account-resource';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private accountSubject = new Subject<string>();
  private bookingsSubject = new Subject<BookingsResource>();
  private accountIdSubject = new Subject<number>();

  constructor(private http: HttpClient, private loginService: LoginService) {
  }

  isLoggedIn() {
    return this.loginService.isLoggedIn();
  }

  sendAccountDetails() {
    this.loginService.getLoginResource().subscribe(value => {
      const accountUrl = value._links['account-details'].href;
      this.http.get<AccountResource>(accountUrl, {headers: this.loginService.setHttpHeaders()}).subscribe(account => {
        this.accountIdSubject.next(account.id);
        this.http.get<BookingsResource>(account._links.bookings.href, {headers: this.loginService.setHttpHeaders()}).subscribe(bookings => {
          this.bookingsSubject.next(bookings);
        });
        this.accountSubject.next(account.passport.firstName);
      });
    });
  }

  getBookings() {
    this.sendAccountDetails();
    return this.bookingsSubject.asObservable();
  }

  login() {
    this.loginService.login();
  }

  getUser() {
    return this.accountSubject.asObservable();
  }

  getAccountId() {
    return this.accountIdSubject.asObservable();
  }

  getBooking(accountId: number, bookingId: string) {
    const url = 'http://localhost:8080/account/' + accountId + '/bookings/' + bookingId;
    return this.http.get<Booking>(url, {headers: this.loginService.setHttpHeaders()});
  }

  logout() {
    this.loginService.logout();
  }
}
