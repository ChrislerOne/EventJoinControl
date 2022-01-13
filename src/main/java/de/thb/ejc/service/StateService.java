package de.thb.ejc.service;

import de.thb.ejc.entity.State;
import de.thb.ejc.entity.User;
import de.thb.ejc.form.StateForm;
import de.thb.ejc.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StateService {
    @Autowired
    private StateRepository stateRepository;

    public State getStateById(int id) {
        return stateRepository.findById(id).get();
    }
    //TODO @Eric

    public ArrayList<User> getUserWithStatus(String state) {
        return stateRepository.findUserByState(state);
    }

    public ArrayList<State> getAllStates() {
        return (ArrayList<State>) stateRepository.findAll();
    }

    public void addState(StateForm stateForm) {
        State state = new State();
        state.setName(stateForm.getName());
    }
}
