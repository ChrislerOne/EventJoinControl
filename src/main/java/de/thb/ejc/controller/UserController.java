package de.thb.ejc.controller;


import com.google.firebase.auth.FirebaseAuthException;
import de.thb.ejc.entity.UserEvent;
import de.thb.ejc.form.EventStateOrgaHelper;
import de.thb.ejc.form.user.UserEventForm;
import de.thb.ejc.service.AuthenticationService;
import de.thb.ejc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import de.thb.ejc.entity.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/user/addtoevent")
    public ResponseEntity addUserToEvent(@RequestParam String idToken, @RequestBody UserEventForm userEventForm) {
        try {
            //try {
            //    String uid = authenticationService.verifyToken(idToken);
            //} catch (FirebaseAuthException fe) {
            //    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            //}
            //ToDo SQL Fehler
            int userid = userEventForm.getUserid();
            int eventid = userEventForm.getEventid();
            userService.addUsertoEvent(userid, eventid);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @PostMapping("/user/delete")
    public ResponseEntity deleteUser(@RequestParam String idToken, @RequestBody int userid) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            userService.deleteUser(userid);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

    }

    @GetMapping("/user/getevents")
    public ResponseEntity getEventsFromUser(@RequestParam String idToken, @RequestParam int userid) {
        try {
//            try {
//                String uid = authenticationService.verifyToken(idToken);
//            } catch (FirebaseAuthException fe) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//            }
            //ToDo auf uid ändern
            Optional<EventStateOrgaHelper> result = userService.getAllEventsFromUser(userid);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

    }
}
