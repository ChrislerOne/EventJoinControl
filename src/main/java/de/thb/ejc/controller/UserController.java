package de.thb.ejc.controller;


import com.google.firebase.auth.FirebaseAuthException;
import de.thb.ejc.entity.*;
import de.thb.ejc.form.orgaUserType.OrgaUserTypeForm;
import de.thb.ejc.form.user.RegisterUserForm;
import de.thb.ejc.form.user.UserEventForm;
import de.thb.ejc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

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


    /**
     * Endpoint for adding the user to selected Event.
     *
     * @param userEventForm for deserializing incoming data from JSON
     * @param idToken       temporary token of user session from frontend
     * @return HTTP CREATED
     */
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
            boolean isAllowed = userService.addUserToEvent(userid, eventid);
            if (isAllowed) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    /**
     * Endpoint for deleting the user from selected Event.
     *
     * @param userEventForm for deserializing incoming data from JSON
     * @param idToken       temporary token of user session from frontend
     * @return HTTP CREATED
     */
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

    /**
     * Endpoint for deleting the selected user.
     *
     * @param idToken temporary token of user session from frontend
     * @param userid  id of selected user
     * @return HTTP OK
     */
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

    /**
     * Endpoint for granting User certain Permission in selected Organization.
     *
     * @param idToken          temporary token of user session from frontend
     * @param orgaUserTypeForm for deserializing incoming data from JSON
     * @return
     */
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

    /**
     * Endpoint for deleting the selected user in the Organization-UserType-User-Table to revoke permission.
     *
     * @param idToken        temporary token of user session from frontend
     * @param orgausertypeid id of selected OrgaUserType
     * @return HTTP OK
     */
    @DeleteMapping("/user/revokeOrganizationPermission")
    public ResponseEntity revokePermission(@RequestParam String idToken, @RequestParam int orgausertypeid) {
        try {
            String uid;
            try {
                uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            userService.revokePermissionToOrganization(orgausertypeid);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

    }

    /**
     * Endpoint for getting every Event where logged-in User is registered.
     *
     * @param idToken temporary token of user session from frontend
     * @return HTTP body with JSON containing the list of events
     */
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

    /**
     * Endpoint for retrieving permissions of every organization where logged-in user is registered.
     *
     * @param idToken temporary token of user session from frontend
     * @return HTTP body with JSON containing the list of Permissions (OrgaUserType)
     */
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


    /**
     * Endpoint for reporting a COVID-19 positive user.
     * The state of every event of the infected user is set to restricted.
     * The state of every user in these restricted events is then set to suspicion of COVID-19.
     * At last the reported user is set to COVID-19 positive.
     *
     * @param idToken temporary token of user session from frontend
     * @return
     */
    @PostMapping("/user/positiveuser")
    public ResponseEntity changeUserStates(@RequestParam String idToken) {
        try {
            String uid;
            try {
                uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            userService.reportPositiveUser(uid);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    /**
     * Endpoint for listing every user with the risk of being infected with COVID-19.
     * It is used for Frontend checks.
     *
     * @param idToken temporary token of user session from frontend
     * @return HTTP body with JSON containing the list of possible infected user
     */
    @GetMapping("/user/listinfecteduser")
    public ResponseEntity listAllInfectedUser(@RequestParam String idToken) {
        try {
            String uid;
            try {
                uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            ArrayList<User> user = userService.getAllInfectedUser();
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    /**
     * Endpoint for checking if the state of the logged-in user should still be suspicion of COVID-19 or COVID-19 positive.
     * If more than 14 days have passed since the change of state it is reset to unverified.
     *
     * @param idToken temporary token of user session from frontend
     * @return HTTP OK
     */
    @PostMapping("/user/refreshstatus")
    public ResponseEntity refreshStatus(@RequestParam String idToken) {
        try {
            String uid;
            try {
                uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            userService.refreshStatus(uid);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    /**
     * Endpoint for changing the status of a certain user
     *
     * @param idToken   temporary token of user session from frontend
     * @param qrtoken   token to identify the user who should be changed
     * @param statename name of the state
     * @return HTTP OK
     */
    @PostMapping("/user/editstatus")
    public ResponseEntity editStatus(@RequestParam String idToken, @RequestParam String qrtoken, @RequestParam String statename) {
        try {
            String uid;
            try {
                uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            userService.changeStateOfUser(qrtoken, statename);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }


    /**
     * Endpoint for retrieving Data of logged-in user.
     *
     * @param idToken temporary token of user session from frontend
     * @return HTTP body with JSON containing the user data
     */
    @GetMapping("/user/getByIdToken")
    public ResponseEntity getUserByIdToken(@RequestParam String idToken) {
        try {
            String uid;
            try {
                uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            User user = userService.getUserByUid(uid);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    /**
     * Endpoint to register User in the Database & create their QRCode.
     * This Endpoint only gets called after the Firebase registration of the user in the Frontend
     *
     * @param registerUserForm for deserializing incoming data from JSON
     * @return HTTP CREATED
     */
    @PostMapping("/authentication/registerUser")
    public ResponseEntity registerUser(@RequestBody RegisterUserForm registerUserForm) {
        try {
            userService.saveUser(registerUserForm);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }
}


