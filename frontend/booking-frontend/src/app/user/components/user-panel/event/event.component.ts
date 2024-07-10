import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CreateEventService } from 'src/app/user/services/create-event.service';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit {

  eventForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private createEventService: CreateEventService) { }

  ngOnInit(): void {

    this.eventForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.min(1)]],
      description: ['', [Validators.required, Validators.min(1)]],
      date: ['', [Validators.required, Validators.min(1)]],
      ticketPricePerTicket: [0, [Validators.required, Validators.min(1)]],
      maxTickets: [0, [Validators.required, Validators.min(1)]]
    });
  }


  onSubmit() {

    const event = {
      ...this.eventForm.value
    }
    this.createEventService.createEvent(event).subscribe({
      next: (response) => {
        alert("Event created successfully!");
      },
      error: (err) => {
        alert("There was an error during event creation");
        console.log(`${err}`);
      }
    })
  }
}
