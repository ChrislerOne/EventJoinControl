package de.thb.ejc.service;

import de.thb.ejc.entity.Event;
import de.thb.ejc.form.events.EditEventForm;
import de.thb.ejc.form.events.EventForm;
import de.thb.ejc.repository.EventRepository;
import de.thb.ejc.repository.OrganizationRepository;
import de.thb.ejc.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private StateRepository stateRepository;


    public void addEvent(EventForm eventForm) {
        Event event = new Event();
        event.setName(eventForm.getName());
        event.setOrganizationId(organizationRepository.findById(eventForm.getOrganizationid()).get());
        event.setStateId(stateRepository.findById(1).get());
        event.setEventStart(eventForm.getEventstart());
        event.setEventEnd(eventForm.getEventend());

        eventRepository.save(event);
    }

    public void editEvent(EditEventForm editEventForm) {
        Event currentEvent = eventRepository.findEventByName(editEventForm.getOldname()).get();
        String newname = editEventForm.getNewname();
        currentEvent.setName(newname);
        eventRepository.save(currentEvent);
    }

    public void deleteEvent(int eventid) {
        if (eventRepository.findById(eventid).isPresent()) {
            Event currentEvent = eventRepository.findById(eventid).get();
            eventRepository.delete(currentEvent);
        }
    }

    public List<Event> getAllEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    public ArrayList<Event> getAllEventsByOrganization(int organizationId) {
        return eventRepository.findEventsByOrganization(organizationId);
    }
}
