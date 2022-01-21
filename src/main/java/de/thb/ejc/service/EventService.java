package de.thb.ejc.service;

import de.thb.ejc.entity.Event;
import de.thb.ejc.form.events.EditEventForm;
import de.thb.ejc.form.events.EventForm;
import de.thb.ejc.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrganizationService organizationService;



    public Event getEventById(int id) {
        return eventRepository.findById(id).get();
    }

    public void addEvent(EventForm eventForm) {
        Event event = new Event();
        event.setName(eventForm.getName());
        event.setOrganizationId(organizationService.getOrganizationById(eventForm.getOrganizationid()));

        eventRepository.save(event);
    }
    public void editEvent(EditEventForm editEventForm){
         Event currentEvent = eventRepository.findEventByName(editEventForm.getOldname()).get();
         String newname = editEventForm.getNewname();
         currentEvent.setName(newname);
         eventRepository.save(currentEvent);
    }

    public void deleteEvent(int eventid){
        Event currentEvent = eventRepository.findById(eventid).get();
        eventRepository.delete(currentEvent);
    }

    public ArrayList<Event> getAllEvents() {
        return (ArrayList<Event>) eventRepository.findAll();
    }


}
