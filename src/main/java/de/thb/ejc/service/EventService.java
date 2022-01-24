package de.thb.ejc.service;

import de.thb.ejc.entity.Event;
import de.thb.ejc.entity.User;
import de.thb.ejc.form.events.EditEventForm;
import de.thb.ejc.form.events.EventForm;
import de.thb.ejc.repository.EventRepository;
import de.thb.ejc.repository.OrganizationRepository;
import de.thb.ejc.repository.StateRepository;
import de.thb.ejc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private UserRepository userRepository;


    public Event getEventById(int eventId) {
        if (eventRepository.findById(eventId).isPresent()) {
            return eventRepository.findById(eventId).get();
        }
        return null;
    }

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

    public void changeStateToPositiv(ArrayList<Event> events) {
        events.forEach(event -> changeEvent(event.getId()));
    }

    public void changeEvent(int eventId) {
        Event event = getEventById(eventId);
        ArrayList<User> userToChange = eventRepository.findAllUserFromEvent(eventId);
        if (stateRepository.findById(7).isPresent()) {
            userToChange.forEach(this::changeUser);
        }
        event.setStateId(stateRepository.findById(3).get());
        eventRepository.save(event);

    }

    public void changeUser(User user) {
        user.setState(stateRepository.findById(7).get());
        user.setStatetimestamp(LocalDateTime.now());
        userRepository.save(user);

    }

    public int countEventUser(int eventid){
        return eventRepository.countAllUserFromEvent(eventid);
    }


}
