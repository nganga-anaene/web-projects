import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {LoginService} from '../services/login.service';
import {Router} from '@angular/router';
import {CookieService} from 'ngx-cookie-service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  private loginForm = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
    password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(50)])
  });
  private registerLabel = 'Don\'t have an account?';
  private loginLabel = 'Login';

  constructor(private loginService: LoginService, private router: Router, private cookieService: CookieService) {
  }

  ngOnInit() {
    this.cookieService.deleteAll();
    if (this.cookieService.check('logged-in')) {
      this.router.navigateByUrl('account');
    }
  }

  onSubmit() {
    if (!this.isInputInvalid()) {
      this.processLogin();
    }
  }

  private setSessionData() {
    this.cookieService.set('username', this.userName.value);
    this.cookieService.set('password', this.password.value);
  }

  private processLogin() {
    this.setSessionData();
    this.loginService.getLoginResource().subscribe(value => {
      this.cookieService.set('logged-in', 'true');
      this.loginService.sendSubjects(value);
      this.router.navigateByUrl('account');
    }, error1 => {
    });
  }

  private processLogout() {
    this.cookieService.delete('username');
    this.cookieService.delete('password');
    this.cookieService.delete('logged-in');
  }

  private isInputInvalid(): boolean {
    return this.userName.invalid && this.password.invalid;
  }

  get userName() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }

  register() {
    this.router.navigateByUrl('account/register');
  }
}
