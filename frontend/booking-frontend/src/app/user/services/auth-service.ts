import { Injectable } from '@angular/core';
import { User } from './user';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import jwt_decode from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  apiUrl: string = "http://localhost:8090/user/current";
  
  constructor(private http:HttpClient, private cookieService: CookieService) { }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  login(token: string): void {
    this.cookieService.set('token', token, {path: '/', 
      secure: true, 
      sameSite: 'Strict',
      expires: new Date(new Date().getTime() + 30 * 24 * 60 * 60 * 1000)});
  }

  getToken(): string | null {
    return this.cookieService.get('token');
  }

  logout(): void {
    this.cookieService.delete('token', '/');
  }

  retrieveUser(token: string) {
    return jwt_decode(token);
  }
}
