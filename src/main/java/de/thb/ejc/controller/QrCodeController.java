package de.thb.ejc.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.zxing.WriterException;
import de.thb.ejc.entity.UserType;
import de.thb.ejc.service.AuthenticationService;
import de.thb.ejc.service.QRCodeService;
import de.thb.ejc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;


@Controller
public class QrCodeController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/qrcode/get")
    public ResponseEntity viewQRCode(@RequestParam String idToken) {
        try {
            String uid = authenticationService.verifyToken(idToken);
            String qrCodeData = userService.getQRCodeDataByUser(uid);
            return ResponseEntity.status(HttpStatus.OK).body(qrCodeData);
        } catch (FirebaseAuthException fe) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }
}
