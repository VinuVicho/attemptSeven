package me.vinuvicho.attemptSeven.registration.token;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.entity.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenDao confirmationTokenDao;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenDao.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenDao.findByToken(token);
    }

    public void setConfirmedAt(String token, LocalDateTime time) {
        confirmationTokenDao.updateConfirmedAt(token, time);
    }

    public ConfirmationToken checkVerifyTokenUnconfirmed(User user) {
        return confirmationTokenDao.findByUserAndTokenTypeAndConfirmedAt(user, TokenType.VERIFY_ACCOUNT, null)
                .orElseThrow();
    }
}
