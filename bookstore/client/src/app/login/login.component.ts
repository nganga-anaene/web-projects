import {Component, OnInit} from '@angular/core';
import {LoginService} from './login.service';
import {Router} from '@angular/router';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {CookieService} from 'ngx-cookie-service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  private loginGroup = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.minLength(6)]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)]),
  });
  private isValid = true;

  constructor(private loginService: LoginService, private router: Router, private cookieService: CookieService) {
  }

  ngOnInit() {
    if (this.loginService.isLoggedIn()) {
      this.processSavedLogin(this.cookieService.get('username'), this.cookieService.get('password'));
    } else {
      this.deleteDetails();
    }
  }

  processLogin() {
    if (this.isFormValid()) {
      this.isValid = true;
      this.loginService.login(this.usernameControl.value, this.passwordControl.value).subscribe(value => {
        this.cookieService.set('username', this.usernameControl.value);
        this.cookieService.set('password', this.passwordControl.value);
        this.cookieService.set('logged-in', 'true');
      }, error1 => console.log(error1));
    } else {
      this.isValid = false;
    }
  }

  isFormValid() {
    return this.passwordControl.valid && this.usernameControl.valid;
  }

  get passwordControl() {
    return this.loginGroup.get('password');
  }

  get usernameControl() {
    return this.loginGroup.get('username');
  }

  private processSavedLogin(username: string, password: string) {
    this.loginService.login(username, password).subscribe(value => {
      this.router.navigateByUrl('/account');
    }, error1 => {
      this.deleteDetails();
    });
  }

  deleteDetails() {
    this.cookieService.delete('username');
    this.cookieService.delete('password');
    this.cookieService.delete('logged-in');
  }
}
