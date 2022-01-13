package de.thb.ejc.service;

import de.thb.ejc.entity.State;
import de.thb.ejc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public State getStateFromUser(String qrToken) {
        //if (userRepository.findStateByQrToken(qrToken).isPresent())
            return userRepository.findStateByQrToken(qrToken).get();
    }
}
