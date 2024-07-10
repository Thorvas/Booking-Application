import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Events } from '../components/user-panel/event-list/event';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CreateEventService {

  apiUrl: string = "http://localhost:8090/event";

  constructor(private http: HttpClient) { }

  createEvent(event: Events): Observable<any> {
    return this.http.post(this.apiUrl, event);
  }
}
