package de.thb.ejc.service;

import de.thb.ejc.entity.UserType;
import de.thb.ejc.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTypeService {

    @Autowired
    private UserTypeRepository userTypeRepository;

    public UserType getUserTypeById(int id) {
        return userTypeRepository.findById(id).get();
    }
}
