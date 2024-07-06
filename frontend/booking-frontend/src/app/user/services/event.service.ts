import { Injectable } from '@angular/core';
import { Events } from '../components/user-panel/event-list/event';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  event!: Events;

  constructor() { }

  setEvent(event: Events) {
    this.event = event;
  }

  getEvent(): Events {
    return this.event;
  }
}
