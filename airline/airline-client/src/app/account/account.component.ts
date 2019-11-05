import {Component, OnInit} from '@angular/core';
import {AccountService} from '../services/account.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {

  private user: string;

  constructor(private accountService: AccountService) {
  }

  ngOnInit() {
    if (!this.accountService.isLoggedIn()) {
      this.accountService.login();
    } else {
      this.setUser();
    }
  }

  private setUser() {
    this.accountService.sendAccountDetails();
    this.accountService.getUser().subscribe(value => this.user = value);
  }
}
