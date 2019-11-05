import {Component, OnInit} from '@angular/core';
import {AppService} from '../app.service';
import {Subject} from 'rxjs';
import {BookResource, BooksResource} from '../resource-interfaces/book-resource';
import {Router} from '@angular/router';

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.scss']
})
export class BooksComponent implements OnInit {

  private bookResources = new Subject<BookResource[]>();
  private maxPages: number;
  private index: number;
  private size: number;
  private pages: number[];
  private resource: BooksResource;
  private currentPage: string;

  constructor(private appService: AppService, private router: Router) {
  }

  ngOnInit() {
    this.appService.getBookResources();
    this.router.events.subscribe(events => this.appService.getBookResources());
    this.requestBookResources();
  }

  requestBookResources() {
    this.appService.getBooksSubject().subscribe(value => {
      this.setResources(value);
    });
  }

  private setPages(booksResource: BooksResource) {
    this.resource = booksResource;
    this.index = booksResource.page.number;
    this.size = booksResource.page.size;
    this.maxPages = booksResource.page.totalPages;
    this.currentPage = booksResource._links.self.href;
    this.pages = [];
    for (let i = 0; i < this.maxPages; i++) {
      this.pages.push(i);
    }
  }

  getImageUrl(br: BookResource) {
    return 'assets/' + br.imageUrl;
  }

  setActive(i: number) {
    if (i === this.index) {
      return 'active-page';
    }
  }

  setResources(value: BooksResource) {
    this.bookResources.next(value._embedded.bookList);
    this.setPages(value);
  }

  getPage(i: number) {
    if (i !== this.index) {
      this.appService.getBooksResourcePage(i, this.size).subscribe(value => {
        this.setResources(value);
      });
    }
  }

  getPreviousPage() {
    if (this.resource._links.prev != null) {
      this.appService.getNextPage(this.resource._links.prev.href).subscribe(value => {
        this.setResources(value);
      });
    }
  }

  getNextPage() {
    if (this.resource._links.next != null) {
      this.appService.getNextPage(this.resource._links.next.href).subscribe(value => {
        this.setResources(value);
      });
    }
  }
}
