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
import {TypeaheadModule} from 'ngx-bootstrap';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';

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
    FontAwesomeModule
  ],
  providers: [CookieService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
