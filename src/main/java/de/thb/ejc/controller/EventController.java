package de.thb.ejc.controller;

import de.thb.ejc.service.EventService;
import de.thb.ejc.entity.Event;
import de.thb.ejc.form.EventForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/events/add")
    public ResponseEntity addEvent(@RequestBody EventForm eventForm) {
        try {
            eventService.addEvent(eventForm);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @GetMapping("/events/list")
    public ResponseEntity listEvents() {
        try {
            ArrayList<Event> events = eventService.getAllEvents();
            return ResponseEntity.status(HttpStatus.OK).body(events);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }
}
