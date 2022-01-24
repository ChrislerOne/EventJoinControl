package de.thb.ejc.controller;


import com.google.firebase.auth.FirebaseAuthException;
import de.thb.ejc.entity.*;
import de.thb.ejc.form.orgaUserType.OrgaUserTypeForm;
import de.thb.ejc.form.user.UserEventForm;
import de.thb.ejc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private OrgaUserTypeService orgaUserTypeService;
    @Autowired
    private UserTypeService userTypeService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private EventService eventService;

    @PostMapping("/user/addtoevent")
    public ResponseEntity addUserToEvent(@RequestBody UserEventForm userEventForm, @RequestParam String idToken) {
        try {
            String uid;
            try {
                uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            int userid = userService.getUserByUid(uid).getId();
            int eventid = userEventForm.getEventid();
            userService.addUsertoEvent(userid, eventid);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @DeleteMapping("/user/deleteFromEvent")
    public ResponseEntity deleteFromEvent(@RequestBody UserEventForm userEventForm, @RequestParam String idToken) {
        try {
            String uid;
            try {
                uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            int userid = userService.getUserByUid(uid).getId();
            int eventid = userEventForm.getEventid();
            userService.deleteUserFromEvent(userid, eventid);
            return ResponseEntity.status(HttpStatus.OK).build();
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
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

    }

    @PostMapping("/user/grantpermission")
    public ResponseEntity grantPermissionToOrganization(@RequestParam String idToken, @RequestBody OrgaUserTypeForm orgaUserTypeForm) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            User user = userService.getUserByEmail(orgaUserTypeForm.getEmail());
            UserType usertypeId = userTypeService.getUserTypeById(orgaUserTypeForm.getUserTypeId());
            Organization organization = organizationService.getOrganizationById(orgaUserTypeForm.getOrganizationId());
            userService.addUserToOrganization(user, organization, usertypeId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

    }

    @GetMapping("/user/getevents")
    public ResponseEntity getEventsFromUser(@RequestParam String idToken) {
        try {
            String uid;
            try {
                uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            ArrayList<Event> result = userService.getAllEventsFromUser(uid);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @GetMapping("/user/permissions")
    public ResponseEntity getAllPermissionsFromUser(@RequestParam String idToken) {
        try {
            String uid;
            try {
                uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            int userid = userService.getUserByUid(uid).getId();
            ArrayList<OrgaUserType> result = orgaUserTypeService.getAllOrgsByUser(userid);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @PostMapping("/user/positiveuser")
    public ResponseEntity changeUserStates(@RequestParam String idToken) {
        String uid;
        try {
            try {
                uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            ArrayList<Event> events = userService.getAllEventsFromUser(uid);
            eventService.changeStateToPositiv(events);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }
}


