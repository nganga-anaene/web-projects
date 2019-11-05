import {Component, OnInit} from '@angular/core';
import {AppService} from '../../app.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Subject} from 'rxjs';
import {BookResource} from '../../resource-interfaces/book-resource';
import {AuthorResource} from '../../resource-interfaces/author-resource';
import {BookItemResource} from '../../resource-interfaces/book-item-resource';
import {CartService} from '../../cart.service';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.scss']
})
export class BookComponent implements OnInit {

  private bookResource = new Subject<BookResource>();
  private bookItemsResource = new Subject<BookItemResource[]>();
  private authorResource = new Subject<AuthorResource>();
  private hasSelectedBookItem = new Subject<boolean>();
  private price: number;

  constructor(private appService: AppService, private route: ActivatedRoute, private cartService: CartService, private router: Router) {
  }

  ngOnInit() {
    this.setBookDetails();
    this.cartService.setCart();
    this.router.events.subscribe(value => {
      this.setBookDetails();
    });
  }

  private setBookDetails() {
    const bookId = this.route.snapshot.paramMap.get('id');
    this.appService.getBookResource(bookId).subscribe((value: BookResource) => {
      this.price = value.price;
      this.appService.getBookItemsResource(value._links.bookItems.href)
        .subscribe(bookItems => {
          this.bookItemsResource.next(bookItems._embedded.bookItemList);
          this.checkBookItemSelected(bookItems._embedded.bookItemList);
        });
      this.appService.getAuthorResource(value._links.author.href).subscribe(author => this.authorResource.next(author));
      this.bookResource.next(value);
    });
  }

  addToBasket(selectedIndex: number, bookItems: BookItemResource[]) {
    const bookItem = bookItems[selectedIndex];
    this.cartService.getShoppingCart().subscribe(cart => {
      const found = cart.bookItems.map(item => item.id).find(id => bookItem.id === id);
      if (!found) {
        cart.bookItems.push(bookItem);
        const simpleCart = this.cartService.getSimpleCart(cart);
        this.cartService.updateShoppingCart(simpleCart).subscribe(value => this.hasSelectedBookItem.next(true));
      }
    });
  }

  private checkBookItemSelected(bookItemList: BookItemResource[]) {
    const bookItemIds = bookItemList.map(item => item.id);
    this.cartService.getShoppingCart().subscribe(cart => {
      for (const bookItemId of bookItemIds) {
        if (cart.bookItems.map(item => item.id).find(id => id === bookItemId)) {
          this.hasSelectedBookItem.next(true);
          break;
        }
      }
    });
  }

  setPrice(bookItems: BookItemResource[], selectedIndex: number) {
    this.price = bookItems[selectedIndex].total;
  }
}
