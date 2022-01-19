package me.vinuvicho.attemptSeven.additional;

import me.vinuvicho.attemptSeven.registration.RegistrationRequest;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Validator {

    public boolean validateRegistrationRequest(RegistrationRequest request) {
        return validateEmail(request.getEmail()) &&
                validateUsername(request.getUsername()) &&
                validatePassword(request.getPassword());
    }


    public boolean validateUsername(String username) {

        return true;
    }
    public boolean validateEmail(String email) {
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
            return true;
        } catch (AddressException ex) {
            return false;
        }
    }
    public boolean validatePassword(String password) {

        return true;
    }
}
