import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  public token = '';
  public loginData = {
    username: '',
    password: ''
  }

  constructor(private loginService: LoginService, private authService: AuthService, private router: Router) { };

  onSubmit() {
    this.loginService.login(this.loginData).subscribe({
      next: (response) => {
        this.token = response.token;
        this.authService.login(this.token);
        this.router.navigate(['/user-panel']);
      },
      error: (err) => {
        console.log(`Registration error: ${err}`)
      }
    })
  }

  setToken(token: string) {
    localStorage.setItem('token', token);
  }

}
