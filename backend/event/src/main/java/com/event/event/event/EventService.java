package com.event.event.event;

import com.event.event.notification.NotificationDTO;
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

        NotificationDTO notificationDTO = NotificationDTO.builder()
                        .userId(event.getAuthorId())
                                .message("Event has been deleted.")
                                        .build();

        eventRepository.delete(event);
        propagateEventDeleted(event);
        propagateNotification(notificationDTO, "notification_created");

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

        NotificationDTO notificationDTO = NotificationDTO.builder()
                        .userId(event.getAuthorId())
                                .message("Event has been created.")
                                        .build();
        propagateEventCreated(event);
        propagateNotification(notificationDTO, "notification_created");

        return convertToDTO(event);
    }

    public EventDTO throwException(String message) {
        throw new RuntimeException(message);
    }
    public EventDTO updateAndConvertEvent(EventDTO eventDTO, EventModel event) {

        NotificationDTO notificationDTO = NotificationDTO.builder()
                        .userId(eventDTO.getAuthorId())
                                .message("Your event has been updated")
                                        .build();
        event.setDate(eventDTO.getDate());
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setTicketPricePerTicket(eventDTO.getTicketPricePerTicket());

        event = eventRepository.save(event);
        propagateEventUpdated(event);
        propagateNotification(notificationDTO, "notification_created");

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

    public void propagateNotification(NotificationDTO notification, String topic) {
        try {
            String request = objectMapper.writeValueAsString(notification);
            kafkaTemplate.send(topic, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        eventDTO.setTicketPricePerTicket(eventModel.getTicketPricePerTicket());
        eventDTO.setName(eventModel.getName());
        eventDTO.setDescription(eventModel.getDescription());
        eventDTO.setAuthorId(eventModel.getAuthorId());

        return eventDTO;
    }

    private EventModel convertToEvent(EventDTO eventDTO) {

        EventModel eventModel = new EventModel();

        eventModel.setDate(eventDTO.getDate());
        eventModel.setName(eventDTO.getName());
        eventModel.setTicketPricePerTicket(eventDTO.getTicketPricePerTicket());
        eventModel.setDescription(eventDTO.getDescription());
        eventModel.setAuthorId(eventDTO.getAuthorId());

        return eventModel;
    }
}
