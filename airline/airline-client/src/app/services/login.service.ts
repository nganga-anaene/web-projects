import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Resource} from 'angular4-hal';
import {Observable, Subject} from 'rxjs';
import {CookieService} from 'ngx-cookie-service';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private loginUrl = 'http://localhost:8080/login';
  private accountUrl = new Subject<string>();

  constructor(private http: HttpClient, private cookieService: CookieService, private router: Router) {
  }

  getLoginResource(): Observable<Resource> {
    return this.http.get<Resource>(this.loginUrl, {headers: this.setHttpHeaders()});
  }

  setHttpHeaders() {
    return new HttpHeaders()
      .append('Content-Type', 'application/hal+json;charset=UTF-8')
      .append('Authorization', 'Basic ' + btoa(this.cookieService.get('username') + ':' + this.cookieService.get('password')));
  }

  sendAccountUrl(accountUrl: string) {
    this.accountUrl.next(accountUrl);
  }

  getAccountSubject(): Observable<string> {
    return this.accountUrl.asObservable();
  }

  sendSubjects(value: Resource) {
    this.accountUrl.next(value._links['account-details'].href);
  }

  getAccountDetails(value: Resource) {
    return this.http.get<Resource>(value._links['account-details'].href, {headers: this.setHttpHeaders()});
  }

  isLoggedIn(): boolean {
    return this.cookieService.check('logged-in');
  }

  login() {
    this.router.navigateByUrl('login');
  }

  logout() {
    this.cookieService.delete('username');
    this.cookieService.delete('password');
    this.cookieService.delete('logged-in');
  }
}
