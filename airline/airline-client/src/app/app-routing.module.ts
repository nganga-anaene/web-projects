import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {AccountComponent} from './account/account.component';
import {BookingsComponent} from './bookings/bookings.component';
import {PaymentCardsComponent} from './payment-cards/payment-cards.component';
import {PassportComponent} from './passport/passport.component';
import {FlightsComponent} from './flights/flights.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'account', component: AccountComponent},
  {path: 'bookings', component: BookingsComponent},
  {path: 'payment-cards', component: PaymentCardsComponent},
  {path: 'passport', component: PassportComponent},
  {path: 'flights', component: FlightsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
