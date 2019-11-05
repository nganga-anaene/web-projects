import {Component, OnInit} from '@angular/core';
import {AppService} from '../../app.service';
import {Subject} from 'rxjs';
import {BookResource, BooksResource, BookTitleResource, BookTitleResources} from '../../resource-interfaces/book-resource';
import {Router} from '@angular/router';
import {faShoppingCart, faUser} from '@fortawesome/free-solid-svg-icons';
import {CookieService} from 'ngx-cookie-service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  storeName = 'Bookstore';
  selected: string;
  books = new Subject<BookResource[]>();
  bookTitles: string[] = [];
  bookTitlesResource: BookTitleResource[] = [];
  faUser = faUser;
  faCheckout = faShoppingCart;
  booksUrl = 'books';
  accountUrl = 'account';
  loginUrl = 'login';
  checkoutUrl = 'checkout'

  constructor(private appService: AppService, private router: Router, private cookieService: CookieService) {
  }

  ngOnInit() {
    this.appService.getBooksSubject().subscribe((books: BooksResource) => {
      this.books.next(books._embedded.bookList);
    });
    this.getBookTitles();
    this.appService.getBookResources();
  }

  getBookTitles() {
    this.appService.getBookTitles().subscribe((resources: BookTitleResources) => {
      this.bookTitles = resources._embedded.stringList.map(item => item.content);
      this.bookTitlesResource = resources._embedded.stringList;
    });
  }

  getBook() {
    const href = this.bookTitlesResource.find(resource => resource.content === this.selected)._links.book.href;
    const strings = href.split('/');
    const id = strings[strings.length - 1];
    this.router.navigateByUrl('/books/' + id);
  }

  resetBook() {
    this.getBookTitles();
  }

  isLoggedIn() {
    return this.cookieService.check('logged-in');
  }

  logout() {
    this.cookieService.delete('username');
    this.cookieService.delete('password');
    this.cookieService.delete('logged-in');
    this.router.navigateByUrl('books');
  }

  isActiveLink(url: string) {
    return window.location.href.toLowerCase().includes(url);
  }
}
