package de.thb.ejc.service;

import de.thb.ejc.entity.Event;
import de.thb.ejc.form.EventForm;
import de.thb.ejc.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event getEventById(int id) {
        return eventRepository.findById(id).get();
    }

    public void addEvent(EventForm eventForm) {
        Event event = new Event();
        event.setName(eventForm.getName());
        eventRepository.save(event);
    }

    public ArrayList<Event> getAllEvents() {
        return (ArrayList<Event>) eventRepository.findAll();
    }

    public void addUsertoEvent(int userId, int eventId){
        //ToDo
    }
}
