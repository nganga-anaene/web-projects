import {Component, OnInit} from '@angular/core';
import {AccountService} from '../../services/account.service';
import {Booking} from '../../resource-interfaces/bookings-resource';
import {Observable, of} from 'rxjs';

@Component({
  selector: 'app-bookings',
  templateUrl: './bookings.component.html',
  styleUrls: ['./bookings.component.scss']
})
export class BookingsComponent implements OnInit {

  private bookings: Observable<Booking[]>;

  constructor(private accountService: AccountService) {
  }

  ngOnInit() {
    this.bookings = new Observable();
    if (this.accountService.isLoggedIn()) {
      this.accountService.getBookingDetails().subscribe(value => {
        this.bookings = of(value._embedded.bookingList);
      });
    } else {
      this.accountService.login();
    }
  }
}
