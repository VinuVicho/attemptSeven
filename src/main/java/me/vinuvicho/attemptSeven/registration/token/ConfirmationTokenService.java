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

    public void deleteToken(String token) {
        confirmationTokenDao.deleteByToken(token);
    }

    public ConfirmationToken getVerifyTokenByUser(User user) {
        return confirmationTokenDao.findByUserAndTokenType(user, TokenType.VERIFY_ACCOUNT)
                .orElseThrow();
    }
}
