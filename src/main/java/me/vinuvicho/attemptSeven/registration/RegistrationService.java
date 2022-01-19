package me.vinuvicho.attemptSeven.registration;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.additional.Validator;
import me.vinuvicho.attemptSeven.email.EmailSender;
import me.vinuvicho.attemptSeven.entity.user.User;
import me.vinuvicho.attemptSeven.entity.user.UserRole;
import me.vinuvicho.attemptSeven.entity.user.UserService;
import me.vinuvicho.attemptSeven.registration.token.ConfirmationToken;
import me.vinuvicho.attemptSeven.registration.token.ConfirmationTokenService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public String register(RegistrationRequest request) {
        if (!new Validator().validateRegistrationRequest(request)) {
            throw new IllegalStateException("Not valid credentials");
        }
        ConfirmationToken token = userService.signUpUser(
            new User(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword(),
                    UserRole.USER
            )
        );
        String confirmLink = "http://localhost:8080/register/confirm?token=" + token.getToken();
        String rejectLink = "http://localhost:8080/register/reject?token=" + token.getToken();
        emailSender.send(request.getEmail(), buildEmail(request.getUsername(), confirmLink, rejectLink));
        return confirmLink;
    }

    @Transactional
    public void confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));
        if (confirmationToken.getConfirmedAt() != null) {
            if (confirmationToken.getConfirmedAt().isAfter(LocalDateTime.now().withYear(666))) {    //кастиль, рік = 1, якщо час сплив, 10 - rejected
                throw new IllegalStateException("Email already confirmed");                         //кастиль вже не потрібний
            }
            if (confirmationToken.getConfirmedAt().getYear() == 11) {
                throw new IllegalStateException("Token already rejected");
            }
        }

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now()) ||
                confirmationToken.getConfirmedAt() != null && confirmationToken.getConfirmedAt().getYear() == 1) {
            throw new IllegalStateException("Token expired");
        }
        confirmationTokenService.setConfirmedAt(token, LocalDateTime.now());
        userService.enableUser(confirmationToken.getUser().getEmail());
    }

    @Transactional
    public void rejectToken(String token) {
        ConfirmationToken rejectedToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));
        if (rejectedToken.getConfirmedAt() != null &&
                rejectedToken.getConfirmedAt().isAfter(LocalDateTime.now().withYear(666))) {    //кастиль, рік = 1, якщо час сплив, 10 - rejected
            throw new IllegalStateException("Token already confirmed");
                    //Block user?
        }
        confirmationTokenService.setConfirmedAt(token, LocalDateTime.now().withYear(-10));       //кастиль
                    //Maybe block email? with special code to make email valid again
    }

    private String buildEmail(String name, String confirmationLink, String rejectLink) {           //TODO make own email
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + confirmationLink + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "<p><a href=\"" + rejectLink + "\">if it wasn't you, press here</p>" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
