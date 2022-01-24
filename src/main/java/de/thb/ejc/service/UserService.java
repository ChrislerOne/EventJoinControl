package de.thb.ejc.service;

import de.thb.ejc.entity.*;
import com.google.firebase.auth.FirebaseAuthException;
import de.thb.ejc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    EventService eventService;

    @Autowired
    StateService stateService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QRCodeRepository qrCodeRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserEventRepository userEventRepository;

    @Autowired
    private OrgaUserTypeRepository orgaUserTypeRepository;

    public UserEvent getSpecificUserEvent(int userid, int eventid) {
        return userEventRepository.getSpecificUserEvent(userid, eventid);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public Event getEventById(int id) {
        return eventRepository.findById(id).get();
    }

    public State getStateFromUser(String qrToken) {
        refreshStatus(getUserByQRToken(qrToken).getUid());
        return userRepository.findStateByQrToken(qrToken).get();
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId).get();
    }

    public User getUserByUid(String uid) {
        return userRepository.findByUid(uid).get();
    }

    public User getUserByQRToken(String qrToken) {
        return userRepository.findUserByQRToken(qrToken);
    }

    public ArrayList<Event> getAllEventsFromUser(String uid) {
        return userRepository.findAllEventsFromUser(uid);
    }

    //TODO Überprüfen der Platzierung
    public String getQRCodeDataByUser(String uid) throws FirebaseAuthException {

        return qrCodeRepository.findByUid(uid).get();

    }

    public void addUserToEvent(int userId, int eventId) {
        Event event = getEventById(eventId);
        User user = getUserById(userId);
        UserEvent userEvent = new UserEvent();

        userEvent.setUserId(user);
        userEvent.setEventId(event);
        userEventRepository.save(userEvent);
    }

    public void deleteUserFromEvent(int userid, int eventid) {
        UserEvent userEvent = userEventRepository.getSpecificUserEvent(userid, eventid);
        userEventRepository.delete(userEvent);
    }

    public void deleteUser(int userid) {
        if (userRepository.findById(userid).isPresent()) {
            User currentUser = userRepository.findById(userid).get();
            userRepository.delete(currentUser);
        }
    }

    public void addUserToOrganization(User user, Organization organization, UserType usertype) {
        OrgaUserType orgaUserType = new OrgaUserType();
        orgaUserType.setOrganization(organization);
        orgaUserType.setUserType(usertype);
        orgaUserType.setUser(user);
        orgaUserTypeRepository.save(orgaUserType);
    }

    public void reportPositiveUser(String uid) {

        ArrayList<Event> events = getAllEventsFromUser(uid);
        eventService.changeStateToPositiv(events);
        User user = getUserByUid(uid);
        user.setState(stateService.getStateById(6));
        user.setStatetimestamp(LocalDateTime.now());
        userRepository.save(user);
    }

    public ArrayList<User> getAllInfectedUser() {
        return userRepository.findAllInfectedUser();
    }

    public void refreshStatus(String uid) {
        User user = getUserByUid(uid);
        if ((user.getState().getId() == 6 || user.getState().getId() == 7) && user.getStatetimestamp().isBefore(LocalDateTime.now().minusDays(14)) ) {
            user.setState(stateService.getStateById(1));
            user.setStatetimestamp(null);
            userRepository.save(user);
        }
    }

}
