import { TestBed } from '@angular/core/testing';

import { BookingOrderService } from './booking-order.service';

describe('BookingOrderService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BookingOrderService = TestBed.get(BookingOrderService);
    expect(service).toBeTruthy();
  });
});
