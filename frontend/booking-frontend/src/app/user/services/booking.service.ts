import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Events } from '../components/user-panel/event-list/event';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  targetUrl: string = "http://localhost:8090/booking";

  constructor(private http:HttpClient) { }

  bookEvent(event:Events): Observable<any> {

    const ticketAmount = event.requestedTickets;
    const totalPrice = event.requestedTickets * event.ticketPricePerTicket;
    const booking = {
      eventId: event.id,
      ticketAmount: ticketAmount,
      totalPrice: totalPrice
    };
    return this.http.post(this.targetUrl, booking);
  }
}
