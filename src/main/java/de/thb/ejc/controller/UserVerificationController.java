package de.thb.ejc.controller;

import de.thb.ejc.entity.State;
import de.thb.ejc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserVerificationController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/getStatus/{token}")
    public State getStatus(
            @PathVariable("token") String authToken) {
        State state;
        state = userService.getStateFromUser(authToken);
        return state;


    }

}
