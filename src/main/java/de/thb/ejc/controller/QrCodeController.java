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

    /**
     * Endpoint for retrieving the data of the own QRCode at any given point.
     * The Base64 data needs to be decoded in Frontend.
     *
     * @param idToken temporary token of user session from frontend
     * @return HTTP Response body with JSON containing the base64 encoded data of the QRCode
     */
    @GetMapping(value = "/qrcode/get")
    public ResponseEntity viewQRCode(@RequestParam String idToken) {
        try {
            String uid;
            try {
                uid = authenticationService.verifyToken(idToken);
            } catch (FirebaseAuthException fe) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            String qrCodeData = userService.getQRCodeDataByUser(uid);
            return ResponseEntity.status(HttpStatus.OK).body(qrCodeData);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }
}
