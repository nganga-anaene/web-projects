import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Resource} from 'angular4-hal';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http: HttpClient) {
  }

  checkUserExists(username): Observable<boolean> {
    return this.http.get<boolean>('http://localhost:8080/user/' + username);
  }

  registerClient(registrationDetails): Observable<Resource> {
    return this.http.post<Resource>('http://localhost:8080/register', registrationDetails);
  }
}
