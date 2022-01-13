package de.thb.ejc.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.zxing.WriterException;
import de.thb.ejc.controller.QrCodeController;
import de.thb.ejc.entity.QRCode;
import de.thb.ejc.entity.User;
import de.thb.ejc.form.RegisterUserForm;
import de.thb.ejc.repository.QRCodeRepository;
import de.thb.ejc.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

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
        String QrToken = DigestUtils.sha256Hex(email);

        User user = new User();
        //TODO Multiple User types on registration
        user.setUserType(userTypeService.getUserTypeById(10));
        user.setState(stateService.getStateById(1));
        user.setUid(uid);
        user.setEmail(email);
        user.setQrToken(QrToken);
        userRepository.save(user);

        //TODO Save Birthdate in QR code to authenticate Person
        QRCode qrCode = new QRCode();
        qrCode.setUser(user);
        qrCode.setFile(getQRCode(QrToken));
        qrCodeRepository.save(qrCode);
    }
    public String getQRCode(String text){
        int width = 350;
        int height = 350;
        byte[] image = new byte[0];
        try {
            String embeddedurl = "localhost:8090/getStatus/" + text;
            image = QrGenerator.getQRCodeImage(embeddedurl, width, height);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        String qrcode = Base64.getEncoder().encodeToString(image);

        return qrcode;
    }
}
