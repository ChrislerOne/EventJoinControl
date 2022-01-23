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
        if (userTypeRepository.findById(id).isPresent()) {
            return userTypeRepository.findById(id).get();
        } else {
            return null;
        }
    }

//    public UserType getUserTypeByUid(String uid) {
//        if (userTypeRepository.findByUid(uid).isPresent()) {
//            return userTypeRepository.findByUid(uid).get();
//        } else {
//            return null;
//        }
//
//    }
}
