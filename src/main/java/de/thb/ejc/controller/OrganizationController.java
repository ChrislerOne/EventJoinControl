package de.thb.ejc.controller;

import com.google.firebase.auth.FirebaseAuthException;
import de.thb.ejc.form.events.EditEventForm;
import de.thb.ejc.form.organization.EditOrganizationForm;
import de.thb.ejc.service.AuthenticationService;
import de.thb.ejc.service.OrganizationService;
import de.thb.ejc.entity.Organization;
import de.thb.ejc.form.organization.OrganizationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private AuthenticationService authenticationService;


    /**
     * @param organizationForm to deserialize JSON into Java class
     * @return ResponseEntity
     */
    @PostMapping("/organizations/add")
    public ResponseEntity addOrganization(@RequestBody OrganizationForm organizationForm, @RequestParam String token) {
        try {
            try {
                String uid = authenticationService.verifyToken(token);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            //ToDo
            organizationService.addOrganization(organizationForm);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @PostMapping("/organizations/edit")
    public ResponseEntity editEvent(@RequestParam String idToken, @RequestBody EditOrganizationForm editOrganizationForm) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            organizationService.editOrganization(editOrganizationForm);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @PostMapping("/organizations/delete")
    public ResponseEntity deleteEvent(@RequestParam String idToken, @RequestBody int organizationid) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            organizationService.deleteOrganization(organizationid);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

    }

    /**
     * @return HttpResponse with JSON containing list of Organizations
     */
    @GetMapping("/organizations/list")
    public ResponseEntity listOrganizations(@RequestParam String idToken) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            ArrayList<Organization> organizations = organizationService.getAllOrganizations();
            return ResponseEntity.status(HttpStatus.OK).body(organizations);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }
}
