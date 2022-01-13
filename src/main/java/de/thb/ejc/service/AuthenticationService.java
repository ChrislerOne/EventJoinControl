package de.thb.ejc.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import de.thb.ejc.entity.User;
import de.thb.ejc.form.RegisterUserForm;
import de.thb.ejc.repository.QRCodeRepository;
import de.thb.ejc.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private StateService stateService;
    @Autowired
    private UserTypeService userTypeService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    QRCodeRepository qrCodeRepository;

    public String verifyToken(String idToken) throws FirebaseAuthException {

        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();
        return uid;
    }

    //TODO Implement
    public UserRecord getUser(String uid) throws FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
        System.out.println("Successfully fetched user data: " + userRecord.getUid());
        return userRecord;
    }

    public void saveUser(RegisterUserForm registerUserForm) {
        String uid = registerUserForm.getUid();
        String email = registerUserForm.getEmail();

        User user = new User();
        //TODO Multiple User types on registration
        user.setUserType(userTypeService.getUserTypeById(10));
        user.setState(stateService.getStateById(1));
        user.setUid(uid);
        user.setEmail(email);
    }
}
