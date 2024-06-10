package com.event.event.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public List<EventDTO> getAllEvents() {

        return eventRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public EventDTO getEvent(Long id) {

        EventModel event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found."));

        return convertToDTO(event);
    }

    public EventDTO deleteAndConvertEvent(EventModel event) {

        eventRepository.delete(event);
        propagateEventDeleted(event);

        return convertToDTO(event);
    }

    public EventDTO deleteEvent(Long id, Jwt jwt) {

        EventModel event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found."));

        return event.getAuthorId().equals(Long.valueOf(jwt.getSubject())) ?
                deleteAndConvertEvent(event) : throwException("You are not an author of event");
    }

    public EventDTO createEvent(EventDTO eventDTO, Jwt jwt) {

        EventModel event = convertToEvent(eventDTO);
        event.setAuthorId(Long.valueOf(jwt.getSubject()));

        event = eventRepository.save(event);
        propagateEventCreated(event);

        return convertToDTO(event);
    }

    public EventDTO throwException(String message) {
        throw new RuntimeException(message);
    }
    public EventDTO updateAndConvertEvent(EventDTO eventDTO, EventModel event) {

        event.setDate(eventDTO.getDate());
        event.setName(eventDTO.getName());

        event = eventRepository.save(event);
        propagateEventUpdated(event);

        return convertToDTO(event);
    }

    public EventDTO updateEvent(EventDTO eventDTO, Long id, Jwt jwt) {

        EventModel event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found."));

        return event.getAuthorId().equals(Long.valueOf(jwt.getSubject())) ?
                updateAndConvertEvent(eventDTO, event) : throwException("You are not an author of event");
    }

    public void propagateEventDeleted(EventModel event) {
        propagate(event, "event_deleted");
    }

    public void propagateEventCreated(EventModel event) {
        propagate(event, "event_created");
    }

    public void propagateEventUpdated(EventModel event) {
        propagate(event, "event_updated");
    }

    private void propagate(EventModel event, String topic) {

        try {
            String request = objectMapper.writeValueAsString(convertToDTO(event));
            kafkaTemplate.send(topic, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private EventDTO convertToDTO(EventModel eventModel) {

        EventDTO eventDTO = new EventDTO();

        eventDTO.setId(eventModel.getId());
        eventDTO.setDate(eventModel.getDate());
        eventDTO.setName(eventModel.getName());
        eventDTO.setAuthorId(eventModel.getAuthorId());

        return eventDTO;
    }

    private EventModel convertToEvent(EventDTO eventDTO) {

        EventModel eventModel = new EventModel();

        eventModel.setDate(eventDTO.getDate());
        eventModel.setName(eventDTO.getName());
        eventModel.setAuthorId(eventDTO.getAuthorId());

        return eventModel;
    }
}
