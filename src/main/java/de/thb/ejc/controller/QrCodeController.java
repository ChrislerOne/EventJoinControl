package de.thb.ejc.controller;

import com.google.zxing.WriterException;
import de.thb.ejc.service.QrGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.Base64;


@Controller
public class QrCodeController {
    private static final int width = 350;
    private static final int height = 350;

    @PostMapping("/QRCode")
    public String getQRCode(@RequestBody String text){

        byte[] image = new byte[0];
        try {
            // Generate and Return Qr Code in Byte Array
            image = QrGenerator.getQRCodeImage(text, width, height);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        String qrcode = Base64.getEncoder().encodeToString(image);

        return qrcode;
    }


    @GetMapping(value = "/genrateQRCode/{codeText}")
    public ResponseEntity<byte[]> generateQRCode(
            @PathVariable("codeText") String codeText)
            throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(QrGenerator.getQRCodeImage(codeText, width, height));
    }

}
