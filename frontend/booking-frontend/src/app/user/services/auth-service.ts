import { Injectable } from '@angular/core';
import { User } from './user';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loggedUser!: User | null;

  apiUrl: string = "http://localhost:8090/user/current";
  
  constructor(private http:HttpClient) { }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  login(token: string): void {
    localStorage.setItem('token', token);
    this.loggedUser = {
      name: "John",
      surname: "Doe"
    }

    console.log(this.loggedUser);
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  retrieveUser(): Observable<any> {
    return this.http.get(this.apiUrl);
  }
}
