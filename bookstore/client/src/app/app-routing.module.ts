import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BooksComponent} from './books/books.component';
import {BookComponent} from './books/book/book.component';
import {CartComponent} from './cart/cart.component';
import {LoginComponent} from './login/login.component';
import {AccountComponent} from './account/account.component';
import {AboutComponent} from './about/about.component';

const routes: Routes = [
  {path: 'books', component: BooksComponent},
  {path: 'books/:id', component: BookComponent},
  {path: 'checkout', component: CartComponent},
  {path: 'login', component: LoginComponent},
  {path: 'account', component: AccountComponent},
  {path: 'about', component: AboutComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
