package de.thb.ejc.controller;

import com.google.firebase.auth.FirebaseAuthException;
import de.thb.ejc.form.event.EditEventForm;
import de.thb.ejc.service.AuthenticationService;
import de.thb.ejc.service.EventService;
import de.thb.ejc.entity.Event;
import de.thb.ejc.form.event.EventForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Endpoint for adding an Event.
     *
     * @param eventForm for deserializing incoming data from JSON
     * @param idToken   temporary token of user session from frontend
     * @return HTTP CREATED
     */
    @PostMapping("/events/add")
    public ResponseEntity addEvent(@RequestBody EventForm eventForm, @RequestParam String idToken) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            eventService.addEvent(eventForm);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    /**
     * Endpoint for editing an Event.
     *
     * @param editEventForm for deserializing incoming data from JSON
     * @param idToken       temporary token of user session from frontend
     * @return HTTP CREATED
     */
    @PostMapping("/events/edit")
    public ResponseEntity editEvent(@RequestParam String idToken, @RequestBody EditEventForm editEventForm) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            eventService.editEvent(editEventForm);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    /**
     * Endpoint for deleting an Event.
     *
     * @param eventId id of event that should be deleted
     * @param idToken temporary token of user session from frontend
     * @return HTTP CREATED
     */
    @DeleteMapping("/events/delete")
    public ResponseEntity deleteEvent(@RequestParam String idToken, @RequestParam int eventId) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            eventService.deleteEvent(eventId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    /**
     * Endpoint for retrieving every Event.
     *
     * @param idToken temporary token of user session from frontend
     * @return HTTP with body containing an JSON with every Event
     */
    @GetMapping("/events/list")
    public ResponseEntity listEvents(@RequestParam String idToken) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            List<Event> events = eventService.getAllEvents();
            return ResponseEntity.status(HttpStatus.OK).body(events);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    /**
     * Endpoint for getting every Event of a certain Organization.
     *
     * @param organizationId ID of selected Organization
     * @param idToken        temporary token of user session from frontend
     * @return HTTP with body containing an JSON with every Event of selected Organization
     */
    @GetMapping("/events/getbyorganization")
    public ResponseEntity listEventsByOrganization(@RequestParam String idToken, @RequestParam int organizationId) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            ArrayList<Event> events = eventService.getAllEventsByOrganization(organizationId);
            return ResponseEntity.status(HttpStatus.OK).body(events);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    /**
     * Enpoint for getting the amount of registered User in selected Event.
     *
     * @param idToken
     * @param eventid
     * @return HTTP with body containing JSON with the amount of user
     */
    @GetMapping("/events/countuser")
    public ResponseEntity countUser(@RequestParam String idToken, @RequestParam int eventid) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            int number = eventService.countEventUser(eventid);
            return ResponseEntity.status(HttpStatus.OK).body(number);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }


}
