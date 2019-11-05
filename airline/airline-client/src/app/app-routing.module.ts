import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {AccountComponent} from './account/account.component';
import {FlightsComponent} from './flights/flights.component';
import {BookingOrderComponent} from './booking-order/booking-order.component';
import {RegisterComponent} from './register/register.component';
import {BookingsComponent} from './account/bookings/bookings.component';
import {BookingDetailComponent} from './account/bookings/booking-detail/booking-detail.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'account', component: AccountComponent},
  {path: 'flights', component: FlightsComponent},
  {path: 'flights/booking', component: BookingOrderComponent},
  {path: 'account/register', component: RegisterComponent},
  {path: 'account/bookings', component: BookingsComponent},
  {path: 'account/bookings/:id', component: BookingDetailComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
