import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {CookieService} from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  loggedIn = 'logged-in';

  static setHttpHeaders(username: string, password: string) {
    return new HttpHeaders()
      .append('Content-Type', 'application/hal+json;charset=UTF-8')
      .append('Authorization', 'Basic ' + btoa(username + ':' + password));
  }

  constructor(private http: HttpClient, private cookieService: CookieService) {
  }

  isLoggedIn(): boolean {
    return this.cookieService.check(this.loggedIn);
  }

  login(username: string, password: string) {
    return this.http.post('http://localhost:8080/login', '', {headers: LoginService.setHttpHeaders(username, password)});
  }
}
