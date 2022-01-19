package de.thb.ejc.controller;

import com.google.firebase.auth.FirebaseAuthException;
import de.thb.ejc.service.AuthenticationService;
import de.thb.ejc.service.OrganizationService;
import de.thb.ejc.entity.Organization;
import de.thb.ejc.form.OrganizationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity addOrganization(@RequestBody OrganizationForm organizationForm, @RequestBody String token) {
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

    /**
     * @return HttpResponse with JSON containing list of Organizations
     */
    @GetMapping("/organizations/list")
    public ResponseEntity listOrganizations(@RequestBody String token) {
        try {
            try {
                String uid = authenticationService.verifyToken(token);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            //ToDo
            ArrayList<Organization> organizations = organizationService.getAllOrganizations();
            return ResponseEntity.status(HttpStatus.OK).body(organizations);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }
}
