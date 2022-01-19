package de.thb.ejc.controller;

import com.google.firebase.auth.FirebaseAuthException;
import de.thb.ejc.entity.UserType;
import de.thb.ejc.service.AuthenticationService;
import de.thb.ejc.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserTypeController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserTypeService userTypeService;

    //TODO Endpoint um UserType als Admin zu ändern

    @PostMapping("/usertype/get")
    public ResponseEntity retrieveUserType(@RequestBody String idToken) {
        try {
            String uid = authenticationService.verifyToken(idToken);
            UserType userType = userTypeService.getUserTypeByUid(uid);
            return ResponseEntity.status(HttpStatus.OK).body(userType);
        } catch (FirebaseAuthException fe) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

    }
}
