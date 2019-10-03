import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightSelectorComponent } from './flight-selector.component';

describe('FlightSelectorComponent', () => {
  let component: FlightSelectorComponent;
  let fixture: ComponentFixture<FlightSelectorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FlightSelectorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FlightSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
