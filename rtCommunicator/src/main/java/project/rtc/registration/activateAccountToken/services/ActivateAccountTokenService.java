package project.rtc.registration.activateAccountToken.services;

import project.rtc.registration.activateAccountToken.dto.ActivateAccountToken;

public interface ActivateAccountTokenService {

    ActivateAccountToken assignNewTokenToAccount(String email);

    ActivateAccountToken findByToken(String token);

    void delete(String email);
}
