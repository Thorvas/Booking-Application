package com.booking.booking.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "event_created", groupId = "booking_listeners")
    public void saveEvent(String request) {

        try {
            Event event = objectMapper.readValue(request, Event.class);
            eventRepository.save(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "event_deleted", groupId = "booking_listeners")
    public void deleteEvent(String request) {

        try {
            Event event = objectMapper.readValue(request, Event.class);
            eventRepository.delete(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "event_updated", groupId = "booking_listeners")
    public void updateEvent(String request) {
        try {
            Event eventRequestDTO = objectMapper.readValue(request, Event.class);

            Event event = eventRepository.findById(eventRequestDTO.getId()).orElseThrow(() -> new EventNotFoundException("No such event found."));

            BeanUtils.copyProperties(eventRequestDTO, event, "id");

            eventRepository.save(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
