import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BookResource, BooksResource} from './resource-interfaces/book-resource';
import {Subject} from 'rxjs';
import {AuthorResource} from './resource-interfaces/author-resource';
import {BookItemsResource} from './resource-interfaces/book-item-resource';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  private booksSubject = new Subject<BooksResource>();
  private booksUrl = 'http://localhost:8080/books';


  constructor(private httpClient: HttpClient) {
  }

  getBookResources() {
    return this.httpClient.get<BooksResource>(this.booksUrl).subscribe(value => this.booksSubject.next(value));
  }

  getBooksSubject() {
    return this.booksSubject.asObservable();
  }

  getBookResource(bookId) {
    return this.httpClient.get<BookResource>(this.booksUrl + '/' + bookId);
  }

  getAuthorResource(href) {
    return this.httpClient.get<AuthorResource>(href);
  }

  getBookItemsResource(href) {
    return this.httpClient.get<BookItemsResource>(href);
  }

  getBookTitles() {
    return this.httpClient.get(this.booksUrl + '/all');
  }

  getBooksResourcePage(page: number, size: number) {
    return this.httpClient.get<BooksResource>(this.booksUrl + '?page=' + page + '&size=' + size);
  }

  getNextPage(href: string) {
    return this.httpClient.get<BooksResource>(href);
  }

  getRecommendedBooks() {
    return this.httpClient.get<BooksResource>(this.booksUrl + '/recommended');
  }
}
