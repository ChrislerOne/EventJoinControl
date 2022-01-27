package de.thb.ejc.controller;

import com.google.firebase.auth.FirebaseAuthException;
import de.thb.ejc.entity.*;
import de.thb.ejc.form.organization.EditOrganizationForm;
import de.thb.ejc.service.*;
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

    @Autowired
    private UserService userService;

    @Autowired
    private UserTypeService userTypeService;

    @Autowired
    private OrgaUserTypeService orgaUserTypeService;

    /**
     * Endpoint for adding an Organization.
     *
     * @param organizationForm for deserializing incoming data from JSON
     * @param idToken          temporary token of user session from frontend
     * @return HTTP CREATED
     */
    @PostMapping("/organizations/add")
    public ResponseEntity addOrganization(@RequestBody OrganizationForm organizationForm, @RequestParam String idToken) {
        String uid;
        try {
            try {
                uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            User user = userService.getUserByUid(uid);
            UserType userType = userTypeService.getUserTypeById(12);
            organizationService.addOrganization(organizationForm, user, userType);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    /**
     * Endpoint for editing an Organization.
     *
     * @param editOrganizationForm for deserializing incoming data from JSON
     * @param idToken              temporary token of user session from frontend
     * @return HTTP CREATED
     */
    @PostMapping("/organizations/edit")
    public ResponseEntity editOrganization(@RequestParam String idToken, @RequestBody EditOrganizationForm editOrganizationForm) {
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

    /**
     * Endpoint for deleting an Organization.
     *
     * @param organizationid id of organization that should be deleted
     * @param idToken        temporary token of user session from frontend
     * @return HTTP CREATED
     */
    @DeleteMapping("/organizations/delete")
    public ResponseEntity deleteOrganization(@RequestParam String idToken, @RequestParam int organizationid) {
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
     * Endpoint for retrieving every Organization.
     *
     * @param idToken temporary token of user session from frontend
     * @return HTTP with body containing an JSON with every Organization
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

    /**
     * Endpoint for retrieving selected Organization.
     *
     * @param idToken        temporary token of user session from frontend
     * @param organizationid id of selected Organization
     * @return HTTP with body containing an JSON with selected Organization
     */
    @GetMapping("/organizations/getbyid")
    public ResponseEntity getSpecificOrganization(@RequestParam String idToken, @RequestBody int organizationid) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            Organization organization = organizationService.getOrganizationById(organizationid);
            return ResponseEntity.status(HttpStatus.OK).body(organization);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    /**
     * Endpoint for retrieving every allowed state for events of a selected Organization.
     *
     * @param idToken        temporary token of user session from frontend
     * @param organizationid id of selected Organization
     * @return JSON containing every possible state for given organization to be allowed for events
     */
    @PostMapping("/organization/listorganizationstates")
    public ResponseEntity listOrganizationStates(@RequestParam String idToken, @RequestBody int organizationid) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            ArrayList<OrganizationState> states = organizationService.getStatesByOrganizationId(organizationid);

            return ResponseEntity.status(HttpStatus.OK).body(states);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    /**
     * Endpoint for adding the selected state as an allowed state for selected organization
     *
     * @param idToken temporary token of user session from frontend
     * @param organizationid id of selected organization
     * @param stateId id of selected state
     * @return HTTP OK
     */
    @PostMapping("/organization/addState")
    public ResponseEntity addStateToOrganization(@RequestParam String idToken, @RequestParam int organizationid, @RequestParam int stateId) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
               organizationService.addStateToOrganization(organizationid, stateId);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    /**
     * Endpoint for retrieving every user allowed to operate in selected Organization.
     *
     * @param idToken        temporary token of user session from frontend
     * @param organizationid id of selected Organization
     * @return JSON containing every possible state for given organization to be allowed for events
     */
    @GetMapping("/organization/user/list")
    public ResponseEntity listAllUserWithPermissionFromOrganization(@RequestParam String idToken, @RequestParam int organizationid) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            ArrayList<OrgaUserType> orgaUserTypesList = orgaUserTypeService.getUsersByOrg(organizationid);

            return ResponseEntity.status(HttpStatus.OK).body(orgaUserTypesList);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

}
