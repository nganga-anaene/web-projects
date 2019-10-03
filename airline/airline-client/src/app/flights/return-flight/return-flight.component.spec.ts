import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReturnFlightComponent } from './return-flight.component';

describe('ReturnFlightComponent', () => {
  let component: ReturnFlightComponent;
  let fixture: ComponentFixture<ReturnFlightComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReturnFlightComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReturnFlightComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
