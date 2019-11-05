import {Component, OnInit} from '@angular/core';
import {AccountService} from './account.service';
import {faAddressCard, faShoppingBasket, faUserCircle} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {

  addressIcon = faAddressCard;
  orderIcon = faShoppingBasket;
  userIcon = faUserCircle;

  constructor(private accountService: AccountService) {
  }

  ngOnInit() {
  }
}
