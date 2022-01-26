package de.thb.ejc.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    /**
     * Verifying the given idToken, i.e. verifying the logged-in user.
     *
     * @param idToken temporary token of user session from frontend
     * @return Firebase UID
     * @throws FirebaseAuthException when idToken could not be verified
     */
    public String verifyToken(String idToken) throws FirebaseAuthException {

        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();
        return uid;
    }


}
