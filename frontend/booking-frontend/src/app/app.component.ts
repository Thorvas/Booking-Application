import { Component, OnInit } from '@angular/core';
import { AuthService } from './user/services/auth-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(public authService: AuthService, private router: Router) { }
  ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      console.log("You are logged");
      this.router.navigate(['/user-panel']);
    } else {
      console.log("You are not logged");
      this.router.navigate(['/login']);
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/register']);
  }
  title = 'booking-frontend';
}
