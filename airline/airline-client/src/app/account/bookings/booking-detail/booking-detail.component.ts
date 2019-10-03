import {Component, OnInit} from '@angular/core';
import {AccountService} from '../../../services/account.service';
import {ActivatedRoute} from '@angular/router';
import {Subject} from 'rxjs';
import {Booking} from '../../../resource-interfaces/bookings-resource';
import * as moment from 'moment';

@Component({
  selector: 'app-booking-detail',
  templateUrl: './booking-detail.component.html',
  styleUrls: ['./booking-detail.component.scss']
})
export class BookingDetailComponent implements OnInit {

  private bookingSubject = new Subject<Booking>();
  private booking: Booking;
  private bookingId;
  private accountId;

  constructor(private accountService: AccountService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.bookingId = this.route.snapshot.paramMap.get('id');
    if (this.accountService.isLoggedIn()) {
      this.accountService.sendAccountDetails();
      this.accountService.getAccountId().subscribe(accountId => {
        this.accountId = accountId;
        this.accountService.getBooking(accountId, this.bookingId).subscribe(value1 => this.bookingSubject.next(value1));
      });
      this.bookingSubject.asObservable().subscribe(value => this.booking = value);
    } else {
      this.accountService.login();
    }
  }

  getFlightDuration(departureTime: any, arrivalTime: any) {
    const flightTimeDuration = moment.duration(moment(arrivalTime).diff(moment(departureTime)));
    const hours = Math.floor(flightTimeDuration.as('hours'));
    const minutes = flightTimeDuration.asMinutes() % 60;
    return hours + ':' + minutes;
  }

  setDate(date: any) {
    return moment(date).format('LLLL');
  }
}
