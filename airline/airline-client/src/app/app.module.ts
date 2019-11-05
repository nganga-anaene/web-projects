import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {AngularHalModule} from 'angular4-hal';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {
  DateAdapter,
  MAT_DATE_FORMATS,
  MAT_DATE_LOCALE,
  MatAutocompleteModule,
  MatButtonModule,
  MatCardModule,
  MatDatepickerModule,
  MatDividerModule,
  MatFormFieldModule,
  MatInputModule,
  MatMenuModule,
  MatSelectModule,
  MatStepperModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule
} from '@angular/material';
import {LoginComponent} from './login/login.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AccountComponent} from './account/account.component';
import {FlightsComponent} from './flights/flights.component';
import {DateSelectorComponent} from './flights/date-selector/date-selector.component';
import {ReturnFlightComponent} from './flights/return-flight/return-flight.component';
import {SingleFlightComponent} from './flights/single-flight/single-flight.component';
import {AirportsComponent} from './flights/airports/airports.component';
import {AirportSelectorComponent} from './flights/airports/airport-selector/airport-selector.component';
import {MatMomentDateModule, MomentDateAdapter} from '@angular/material-moment-adapter';
import {FlightSelectorComponent} from './flights/flight-selector/flight-selector.component';
import {BookingOrderComponent} from './booking-order/booking-order.component';
import {DestinationComponent} from './flights/flight-selector/destination/destination.component';
import {RegisterComponent} from './register/register.component';
import {CookieService} from 'ngx-cookie-service';
import {BookingsComponent} from './account/bookings/bookings.component';
import {BookingDetailComponent} from './account/bookings/booking-detail/booking-detail.component';
import {NavHeaderComponent} from './navigation/nav-header/nav-header.component';
import {NavFooterComponent} from './navigation/nav-footer/nav-footer.component';
import {FeaturedComponent} from './flights/featured/featured.component';

export const MY_FORMATS = {
  parse: {
    dateInput: 'LL',
  },
  display: {
    dateInput: 'LL',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY',
  },
};

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AccountComponent,
    FlightsComponent,
    DateSelectorComponent,
    ReturnFlightComponent,
    SingleFlightComponent,
    AirportsComponent,
    AirportSelectorComponent,
    FlightSelectorComponent,
    BookingOrderComponent,
    DestinationComponent,
    RegisterComponent,
    BookingsComponent,
    BookingDetailComponent,
    NavHeaderComponent,
    NavFooterComponent,
    FeaturedComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatFormFieldModule,
    AngularHalModule.forRoot(),
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatTabsModule,
    MatAutocompleteModule,
    MatDatepickerModule,
    MatMomentDateModule,
    MatDividerModule,
    MatStepperModule,
    MatSelectModule,
    MatTableModule,
    MatMenuModule,
    MatToolbarModule,
    MatTooltipModule,
  ],
  providers: [
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MY_FORMATS}, CookieService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
