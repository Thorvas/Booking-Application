import { Component } from '@angular/core';
import { RegisterService} from '../../services/register.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  exampleData = "Something";
  registerData = {username: '', password: ''};
  message = '';
  something = true;

  constructor(private registerService: RegisterService, private router: Router) { }

  onSubmit() {
    this.registerService.register(this.registerData).subscribe({
      next: (response) => {
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.log(`Registration error ${err}`);
        this.message = 'Registration failed.';
      }
    });
  }
}
