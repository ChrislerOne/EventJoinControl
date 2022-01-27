package de.thb.ejc.controller;

import com.google.firebase.auth.FirebaseAuthException;
import de.thb.ejc.entity.State;
import de.thb.ejc.form.state.StateForm;
import de.thb.ejc.service.AuthenticationService;
import de.thb.ejc.service.StateService;
import de.thb.ejc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class StateController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private StateService stateService;

    @Autowired
    private UserService userService;


    /**
     * Endpoint for checking state of User through the QRCode.
     * It is not necessary that a user must be logged in.
     *
     * @param qrToken SHA-256 Hash String of USer Email, URL with specific token is stored in the QRCode
     * @return serialized JSON containing the state of the user
     */
    @GetMapping(value = "/getStatus/{token}")
    public State getStatus(
            @PathVariable("token") String qrToken) {
        State state;
        state = userService.getStateFromUser(qrToken);
        return state;
    }

    /**
     * Endpoint for adding a State.
     *
     * @param stateForm for deserializing incoming data from JSON
     * @param idToken   temporary token of user session from frontend
     * @return HTTP CREATED
     */
    @PostMapping("/states/add")
    public ResponseEntity addState(@RequestBody StateForm stateForm, @RequestBody String idToken) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            stateService.addState(stateForm);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    /**
     * Endpoint for retrieving every State.
     *
     * @param idToken temporary token of user session from frontend
     * @return HTTP with body containing an JSON with every State
     */
    @GetMapping("/states/list")
    public ResponseEntity listStates(@RequestParam String idToken) {
        try {
            try {
                String uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            ArrayList<State> states = stateService.getAllStates();
            return ResponseEntity.status(HttpStatus.OK).body(states);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }


}
