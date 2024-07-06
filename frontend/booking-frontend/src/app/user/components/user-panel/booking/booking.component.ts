import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { BookingService } from 'src/app/user/services/booking.service';
import { Events } from '../event-list/event';
import { EventService } from 'src/app/user/services/event.service';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css']
})
export class BookingComponent implements OnInit {

  bookingForm!: FormGroup;
  event!: Events;

  constructor(private fb: FormBuilder, private route:ActivatedRoute, private bookingService: BookingService, private eventService: EventService) { }

  ngOnInit(): void {
    this.event = this.eventService.getEvent();

    this.bookingForm = this.fb.group({
      ticketAmount: [null, Validators.required],
      totalPrice: [{value: null , disabled: true}]
    });


    this.bookingForm.get('ticketAmount')?.valueChanges.subscribe(value => {
      const ticketPrice = this.event.ticketPricePerTicket;

      this.bookingForm.patchValue({
        totalPrice: value * ticketPrice
      });

      this.event.requestedTickets = value;
    })
  }

  onSubmit() {
    this.bookingService.bookEvent(this.event).subscribe({

      next: (response) => {
        alert("Booking succeeded");
      },
      error: (err) => {
        alert("Booking error");
      }
    })
  }

}
