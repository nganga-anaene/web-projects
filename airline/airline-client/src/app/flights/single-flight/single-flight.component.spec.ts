import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleFlightComponent } from './single-flight.component';

describe('SingleFlightComponent', () => {
  let component: SingleFlightComponent;
  let fixture: ComponentFixture<SingleFlightComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SingleFlightComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SingleFlightComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
