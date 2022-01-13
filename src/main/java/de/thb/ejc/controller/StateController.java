package de.thb.ejc.controller;

import de.thb.ejc.entity.State;
import de.thb.ejc.form.StateForm;
import de.thb.ejc.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class StateController {

    @Autowired
    private StateService stateService;

    @PostMapping("/states/add")
    public ResponseEntity addState(@RequestBody StateForm stateForm) {
        try {
            stateService.addState(stateForm);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @GetMapping("/states/list")
    public ResponseEntity listStates() {
        try {
            ArrayList<State> states = stateService.getAllStates();
            return ResponseEntity.status(HttpStatus.OK).body(states);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }



}
