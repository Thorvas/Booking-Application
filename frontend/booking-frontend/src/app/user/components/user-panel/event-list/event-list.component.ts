import { Component, OnInit } from '@angular/core';
import { EventListService } from 'src/app/user/services/event-list.service';
import { Events } from './event';
import { BookingService } from 'src/app/user/services/booking.service';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.css']
})
export class EventListComponent implements OnInit {

  public eventList: Events[] = [];

  constructor(private eventListService: EventListService, private bookingService: BookingService) { }

  ngOnInit(): void {
    this.eventListService.getEvents().subscribe({
      next: (response: Events[]) => {
        this.eventList = response;
      },
      error: (err) => {
        console.error(`Error fetching events ${err}`);
      }
    })

  }

  bookEvent(event: Events) {
    this.bookingService.bookEvent(event).subscribe({
      next: (response) => {
        alert("Booking created successfully!");
      },
      error: (err) => {
        console.log(`Booking error ${err}`);
      }
    })
  }

}
