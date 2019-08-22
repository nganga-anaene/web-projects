import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {AngularHalModule} from 'angular4-hal';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {
  MatAutocompleteModule,
  MatButtonModule,
  MatCardModule,
  MatDatepickerModule,
  MatFormFieldModule,
  MatInputModule, MatNativeDateModule,
  MatTabsModule
} from '@angular/material';
import { LoginComponent } from './login/login.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { AccountComponent } from './account/account.component';
import { BookingsComponent } from './bookings/bookings.component';
import { PassportComponent } from './passport/passport.component';
import { PaymentCardsComponent } from './payment-cards/payment-cards.component';
import { FlightsComponent } from './flights/flights.component';
import { AirportChooserComponent } from './flights/airport-chooser/airport-chooser.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AccountComponent,
    BookingsComponent,
    PassportComponent,
    PaymentCardsComponent,
    FlightsComponent,
    AirportChooserComponent,
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
    MatNativeDateModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
