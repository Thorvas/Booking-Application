import { Component } from '@angular/core';
import { RegisterService} from '../../services/register.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerData = {username: '', password: ''};
  message = '';
  something = true;

  constructor(private registerService: RegisterService) { }

  onSubmit() {
    this.registerService.register(this.registerData).subscribe({
      next: (response) => {
        this.message = `Registration successful!`;
      },
      error: (err) => {
        console.log(`Registration error ${err}`);
        this.message = 'Registration failed.';
      }
    });
  }
}
