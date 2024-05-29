package com.event.event.event;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(String name) {
        super(name);
    }
}
