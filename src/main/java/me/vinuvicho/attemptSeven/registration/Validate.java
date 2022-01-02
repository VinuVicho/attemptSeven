package me.vinuvicho.attemptSeven.registration;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Validate {

    public boolean validateEmail(String email) {
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException ex) {
            return false;
        }
        return true;
    }
}
