package me.vinuvicho.attemptSeven.registration;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Validate {

    public boolean validateEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
