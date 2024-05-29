package com.event.event.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

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

        return convertToDTO(event);
    }

    public EventDTO createEvent(EventDTO eventDTO) {

        EventModel eventModel = convertToEvent(eventDTO);

        eventRepository.save(eventModel);

        return convertToDTO(eventModel);

    }

    public EventDTO updateEvent(EventDTO EventDTO, Long id) {

        EventModel event = eventRepository.findById(id).orElseThrow( () -> new EventNotFoundException("Event not found."));

        event.setDate(EventDTO.getDate());
        event.setName(EventDTO.getName());

        eventRepository.save(event);

        return convertToDTO(event);
    }

    private EventDTO convertToDTO(EventModel EventModel) {

        EventDTO eventDTO = new EventDTO();

        eventDTO.setId(EventModel.getId());
        eventDTO.setDate(EventModel.getDate());
        eventDTO.setName(EventModel.getName());

        return eventDTO;
    }

    private EventModel convertToEvent(EventDTO EventDTO) {

        EventModel eventModel = new EventModel();

        eventModel.setDate(EventDTO.getDate());
        eventModel.setName(EventDTO.getName());

        return eventModel;
    }
}
