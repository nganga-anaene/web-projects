import {Component, OnInit} from '@angular/core';
import {BookingOrderService} from './booking-order.service';
import {Router} from '@angular/router';
import {CookieService} from 'ngx-cookie-service';
import {FlightResource} from '../resource-interfaces/flight-resource';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AccountService} from '../services/account.service';

@Component({
  selector: 'app-booking-order',
  templateUrl: './booking-order.component.html',
  styleUrls: ['./booking-order.component.scss']
})
export class BookingOrderComponent implements OnInit {

  private forwardFlight: FlightResource;
  private returnFlight: FlightResource;
  private formGroup = new FormGroup({
    passportNumber: new FormControl('', Validators.required),
    paymentCardName: new FormControl('', [Validators.required, Validators.minLength(10)]),
    paymentCardNumber: new FormControl('', [Validators.required, Validators.minLength(10)])
  });

  constructor(private bookingService: BookingOrderService, private router: Router, private cookieService: CookieService, private accountService: AccountService) {
  }

  ngOnInit() {
    this.checkFlightsSelected();
    this.checkLoggedIn();
  }

  private checkFlightsSelected() {
    if (!this.bookingService.isForwardFlightSelected()) {
      this.router.navigateByUrl('flights');
    } else {
      this.forwardFlight = this.bookingService.getForwardFlight;
    }
    if (this.bookingService.isReturnFlightSelected()) {
      this.returnFlight = this.bookingService.getReturnFlight;
    }
  }

  private checkLoggedIn() {
    if (!this.cookieService.check('logged-in')) {
      this.router.navigateByUrl('login');
    }
  }

  getTotal(): number {
    const forwardPrice = this.forwardFlight.price;
    if (this.returnFlight != null) {
      return this.returnFlight.price + forwardPrice;
    } else {
      return forwardPrice;
    }
  }

  processPayment() {
    this.accountService.sendAccountDetails();
    this.accountService.getUser()
  }

  cancelOrder() {
    this.bookingService.resetReturnFlight();
    this.bookingService.resetForwardFlight();
    this.router.navigateByUrl('flights');
  }
}
