package de.thb.ejc.controller;

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

    /**
     *
     * @param organizationForm to deserialize JSON into Java class
     * @return ResponseEntity
     */
    @PostMapping("/organizations/add")
    public ResponseEntity addOrganization(@RequestBody OrganizationForm organizationForm) {
        try {
            organizationService.addOrganization(organizationForm);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    /**
     *
     * @return HttpResponse with JSON containing list of Organizations
     */
    @GetMapping("/organizations/list")
    public ResponseEntity listOrganizations() {
        try {
            ArrayList<Organization> organizations = organizationService.getAllOrganizations();
            return ResponseEntity.status(HttpStatus.OK).body(organizations);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }
}
