import { Component, OnInit } from '@angular/core';
import { EventListService } from 'src/app/user/services/event-list.service';
import { Events } from './event';
import { BookingService } from 'src/app/user/services/booking.service';
import { Router } from '@angular/router';
import { EventService } from 'src/app/user/services/event.service';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.css']
})
export class EventListComponent implements OnInit {

  public eventList: Events[] = [];
  bookingForm!: FormGroup;
  eventsForms: FormArray = this.fb.array([]);

  constructor(private eventListService: EventListService, private bookingService: BookingService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.eventListService.getEvents().subscribe({
      next: (response: Events[]) => {
        this.eventList = response;
        this.initEventForms();
      },
      error: (err) => {
        console.error(`Error fetching events ${err}`);
      }
    })
  }

  initEventForms(): void { 
    this.eventList.forEach(event => {
      const eventForm = this.fb.group({
        ticketAmount: [0, [Validators.required, Validators.min(1)]],
        totalPrice: [{value: 0, disabled: true}]
      });

      eventForm.get('ticketAmount')?.valueChanges.subscribe(value => {
        const ticketPrice = event.ticketPricePerTicket;
        const ticketAmount = value ?? 0;

        eventForm.patchValue({
          totalPrice: ticketPrice * ticketAmount
        });
        event.requestedTickets = value ?? 0;
      });

      this.eventsForms.push(eventForm);
    })
  }

  getEventForm(index: number): FormGroup {
    return this.eventsForms.at(index) as FormGroup;
  }

  bookEvent(event: Events, index: number) {
    this.bookingService.bookEvent(event).subscribe({
      next: (response) => {
        alert(`Event booked successfully!`);
      },
      error: (err) => {
        alert(`Booking unsuccessful.`);
      }
    })
  }
}
