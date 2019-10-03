import {Component, OnInit} from '@angular/core';
import {BookingOrderService} from './booking-order.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-booking-order',
  templateUrl: './booking-order.component.html',
  styleUrls: ['./booking-order.component.scss']
})
export class BookingOrderComponent implements OnInit {

  constructor(private bookingService: BookingOrderService, private router: Router) {
  }

  ngOnInit() {
    this.checkFlightsSelected();
    this.checkLoggedIn();
  }

  private checkFlightsSelected() {
    if (this.bookingService.getForwardFlight == null) {
      this.router.navigateByUrl('flights');
    }
  }

  private checkLoggedIn() {

  }
}
