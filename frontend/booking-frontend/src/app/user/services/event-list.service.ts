import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Events } from '../components/user-panel/event-list/event';

@Injectable({
  providedIn: 'root'
})
export class EventListService {

  private apiUrl = "http://localhost:8090/event";

  constructor(private http: HttpClient) { }

  getEvents(): Observable<Events[]> {
    return this.http.get<Events[]>(this.apiUrl);
  }
}
