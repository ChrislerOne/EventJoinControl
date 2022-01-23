package de.thb.ejc.controller;

import com.google.firebase.auth.FirebaseAuthException;
import de.thb.ejc.entity.Organization;
import de.thb.ejc.entity.State;
import de.thb.ejc.entity.UserType;
import de.thb.ejc.service.AuthenticationService;
import de.thb.ejc.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class UserTypeController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserTypeService userTypeService;

    @GetMapping("/usertypes/list")
    public ResponseEntity getAllUserTypes(@RequestParam String idToken) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            ArrayList<UserType> userTypeArrayList = userTypeService.getAllUserTypes();
            return ResponseEntity.status(HttpStatus.OK).body(userTypeArrayList);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

}
