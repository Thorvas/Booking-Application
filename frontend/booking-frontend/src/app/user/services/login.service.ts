import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private loginUrl = "http://localhost:8090/user/login";

  constructor(private http: HttpClient) { }

  login(user: {username: string, password: string}): Observable<any> {
    return this.http.post(this.loginUrl, user);
  }

}
