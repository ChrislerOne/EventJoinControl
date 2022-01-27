package de.thb.ejc.service;

import com.google.zxing.WriterException;
import de.thb.ejc.entity.*;
import com.google.firebase.auth.FirebaseAuthException;
import de.thb.ejc.form.user.RegisterUserForm;
import de.thb.ejc.repository.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private EventService eventService;

    @Autowired
    private StateService stateService;

    @Autowired
    private UserTypeService userTypeService;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

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

    @Autowired
    private OrganizationStateRepository organizationStateRepository;

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

    public String getQRCodeDataByUser(String uid) throws FirebaseAuthException {

        return qrCodeRepository.findByUid(uid).get();

    }

    public boolean addUserToEvent(int userId, int eventId) throws Exception {
        Event event = getEventById(eventId);
        User user = getUserById(userId);
        UserEvent userEvent = new UserEvent();
        int userStateId = user.getState().getId();
        int orgStateId;

        Iterable<OrganizationState> organizationStateList = organizationStateRepository.findStatesByOrganizationIdAsIterable(event.getOrganizationId().getId());

        for (OrganizationState orgState : organizationStateList) {
            orgStateId = orgState.getStateId().getId();
            if (Objects.equals(orgStateId, userStateId)) {
                userEvent.setUserId(user);
                userEvent.setEventId(event);
                userEventRepository.save(userEvent);
                return true;
            }
        }

        return false;
    }


    public void deleteUserFromEvent(int userid, int eventid) {
        UserEvent userEvent = userEventRepository.getSpecificUserEvent(userid, eventid);
        userEventRepository.delete(userEvent);
    }

    public void revokePermissionToOrganization(int orgausertypeid) {
        OrgaUserType orgaUserType = orgaUserTypeRepository.findById(orgausertypeid);
        orgaUserTypeRepository.delete(orgaUserType);
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
        eventService.changeStateToPositive(events);
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
        if ((user.getState().getId() == 6 || user.getState().getId() == 7) && user.getStatetimestamp().isBefore(LocalDateTime.now().minusDays(14))) {
            user.setState(stateService.getStateById(1));
            user.setStatetimestamp(null);
            userRepository.save(user);
        }
    }

    public void changeStateOfUser(String qrToken, String stateName) {
        User user = userRepository.findUserByQRToken(qrToken);
        user.setState(stateRepository.findByName(stateName));
        userRepository.save(user);
    }

    public void saveUser(RegisterUserForm registerUserForm) throws FirebaseAuthException {
        String uid = authenticationService.verifyToken(registerUserForm.getIdToken());
        String email = registerUserForm.getEmail();
        String qrToken = DigestUtils.sha256Hex(email);

        User user = new User();
        user.setState(stateService.getStateById(1));
        user.setUid(uid);
        user.setEmail(email);
        user.setQrToken(qrToken);
        userRepository.save(user);

        QRCode qrCode = new QRCode();
        qrCode.setUser(user);
        qrCode.setFile(setQRCode(qrToken));
        qrCodeRepository.save(qrCode);

        //SET USER TYPE FOR EVERY ORGANIZATION
        Iterable<Organization> allOrgs = organizationRepository.findAll();
        for (Organization org : allOrgs) {
            OrgaUserType orgaUserType = new OrgaUserType();
            orgaUserType.setUser(user);
            orgaUserType.setUserType(userTypeService.getUserTypeById(10));
            orgaUserType.setOrganization(org);
            orgaUserTypeRepository.save(orgaUserType);
        }
    }

    public String setQRCode(String text) {
        int width = 350;
        int height = 350;
        byte[] image = new byte[0];
        try {
            String embeddedurl = "https://www.ev-jo-co.xn--fd-fkaa.de/checkQrCode/" + text;
            image = QRCodeService.getQRCodeImage(embeddedurl, width, height);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        String qrcode = Base64.getEncoder().encodeToString(image);

        return qrcode;
    }

}
