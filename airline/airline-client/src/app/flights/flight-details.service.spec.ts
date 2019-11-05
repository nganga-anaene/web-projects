import { TestBed } from '@angular/core/testing';

import { FlightDetails } from './flight-details';

describe('FlightDetails', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FlightDetails = TestBed.get(FlightDetails);
    expect(service).toBeTruthy();
  });
});
