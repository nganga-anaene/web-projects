import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './navigation/header/header.component';
import {BooksComponent} from './books/books.component';
import {AuthorsComponent} from './authors/authors.component';
import {AngularHalModule} from 'angular4-hal';
import {HttpClientModule} from '@angular/common/http';
import {BookComponent} from './books/book/book.component';
import {CookieService} from 'ngx-cookie-service';
import { CartComponent } from './cart/cart.component';
import { LoginComponent } from './login/login.component';
import { AccountComponent } from './account/account.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AboutComponent } from './about/about.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BsDropdownModule, CarouselModule, TypeaheadModule} from 'ngx-bootstrap';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import { BannerComponent } from './banner/banner.component';
import { RecommendedBooksComponent } from './recommended-books/recommended-books.component';
import { BookDetailsComponent } from './books/book-details/book-details.component';
import { FooterComponent } from './navigation/footer/footer.component';
import { OrdersComponent } from './account/orders/orders.component';
import { AddressesComponent } from './account/addresses/addresses.component';
import { UserComponent } from './account/user/user.component';
import { OrderComponent } from './account/orders/order/order.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BooksComponent,
    AuthorsComponent,
    BookComponent,
    CartComponent,
    LoginComponent,
    AccountComponent,
    AboutComponent,
    BannerComponent,
    RecommendedBooksComponent,
    BookDetailsComponent,
    FooterComponent,
    OrdersComponent,
    AddressesComponent,
    UserComponent,
    OrderComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AngularHalModule.forRoot(),
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    TypeaheadModule.forRoot(),
    FormsModule,
    FontAwesomeModule,
    CarouselModule,
    BsDropdownModule.forRoot()
  ],
  providers: [CookieService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
