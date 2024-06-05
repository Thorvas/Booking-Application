package com.event.event.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
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

    public EventDTO deleteEvent(Long id) {

        EventModel event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found."));

        eventRepository.delete(event);
        propagateEventDeleted(event);

        return convertToDTO(event);
    }

    public EventDTO createEvent(EventDTO eventDTO) {

        EventModel event = convertToEvent(eventDTO);

        event = eventRepository.save(event);
        propagateEventCreated(event);

        return convertToDTO(event);
    }

    public EventDTO updateEvent(EventDTO EventDTO, Long id) {

        EventModel event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found."));

        event.setDate(EventDTO.getDate());
        event.setName(EventDTO.getName());

        event = eventRepository.save(event);
        propagateEventUpdated(event);

        return convertToDTO(event);
    }

    public void propagateEventDeleted(EventModel event) {

        EventDTO requestDTO = convertToDTO(event);
        String request = null;

        try {
            request = objectMapper.writeValueAsString(requestDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        kafkaTemplate.send("event_deleted", request);
    }

    public void propagateEventCreated(EventModel event) {

        EventDTO requestDTO = convertToDTO(event);
        String request = null;

        requestDTO.setId(event.getId());
        requestDTO.setName(event.getName());
        requestDTO.setDate(event.getDate());
        requestDTO.setAuthorId(event.getAuthorId());

        try {
            request = objectMapper.writeValueAsString(requestDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        kafkaTemplate.send("event_created", request);
    }

    public void propagateEventUpdated(EventModel event) {

        EventDTO requestDTO = convertToDTO(event);

        requestDTO.setId(event.getId());
        requestDTO.setName(event.getName());
        requestDTO.setDate(event.getDate());
        requestDTO.setAuthorId(event.getAuthorId());

        String request = null;
        try {
            request = objectMapper.writeValueAsString(requestDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        kafkaTemplate.send("event_updated", request);
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
