import {Component, OnInit} from '@angular/core';
import {AccountService} from '../../services/account.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-nav-header',
  templateUrl: './nav-header.component.html',
  styleUrls: ['./nav-header.component.scss']
})
export class NavHeaderComponent implements OnInit {

  constructor(private accountService: AccountService, private router: Router) {
  }

  ngOnInit() {
  }

  isLoggedIn() {
    return this.accountService.isLoggedIn();
  }

  logout() {
    this.accountService.logout();
    this.router.navigateByUrl('/flights');
  }
}
