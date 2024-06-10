package com.event.event.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventDTO>> getEvents() {

        List<EventDTO> event = eventService.getAllEvents();

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDTO> getEvent(@PathVariable Long id) {

        EventDTO event = eventService.getEvent(id);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO, @AuthenticationPrincipal Jwt jwt) {

        EventDTO event = eventService.createEvent(eventDTO, jwt);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO, @AuthenticationPrincipal Jwt jwt) {

        EventDTO event = eventService.updateEvent(eventDTO, id, jwt);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDTO> deleteEvent(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {

        EventDTO event = eventService.deleteEvent(id, jwt);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }
}
