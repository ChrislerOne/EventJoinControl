package de.thb.ejc.controller;

import com.google.firebase.auth.FirebaseAuthException;
import de.thb.ejc.service.AuthenticationService;
import de.thb.ejc.form.RegisterUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    //TODO Check if works with GET
    @GetMapping("/authentication/checkAuth")
    public ResponseEntity checkAuth(@RequestBody String token) {
        try {
            String uid = authenticationService.verifyToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(uid);
        } catch (FirebaseAuthException f) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }


    /**
     * Register User in Database after frontend Firebase registration
     */
    @PostMapping("/authentication/registerUser")
    public ResponseEntity registerUser(@RequestBody RegisterUserForm registerUserForm) {
        try {
            authenticationService.saveUser(registerUserForm);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }
}
