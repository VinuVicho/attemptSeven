package me.vinuvicho.attemptSeven.additional;

import me.vinuvicho.attemptSeven.registration.RegistrationRequest;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Validator {

    public boolean validateRegistrationRequest(RegistrationRequest request) {


        return true;
    }

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
