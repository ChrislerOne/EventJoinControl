package de.thb.ejc.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseAdminConfig {

    @Bean
    FirebaseApp createFirebaseApp() throws IOException {
        ClassPathResource resource = new ClassPathResource("spring.application.json");
        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(resource.getInputStream())).build();
        return FirebaseApp.initializeApp(options);
    }
}
