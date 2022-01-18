package de.thb.ejc.service;

import com.google.firebase.auth.FirebaseAuthException;
import de.thb.ejc.entity.QRCode;
import de.thb.ejc.entity.State;
import de.thb.ejc.entity.UserType;
import de.thb.ejc.repository.QRCodeRepository;
import de.thb.ejc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QRCodeRepository qrCodeRepository;

    public State getStateFromUser(String qrToken) {
        //if (userRepository.findStateByQrToken(qrToken).isPresent())
        return userRepository.findStateByQrToken(qrToken).get();
    }

    public UserType getUserType(String idToken) throws FirebaseAuthException {
        String uid = authenticationService.verifyToken(idToken);
        return userRepository.findUserType(uid).get();

    }

    //TODO Überprüfen der Platzierung
    public QRCode getQRCodeByUser(String idToken) throws FirebaseAuthException {
        String uid = authenticationService.verifyToken(idToken);
        return qrCodeRepository.findByUid(uid).get();
    }
}
