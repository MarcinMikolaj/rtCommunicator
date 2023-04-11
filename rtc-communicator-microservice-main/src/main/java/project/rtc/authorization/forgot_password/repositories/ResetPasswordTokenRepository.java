package project.rtc.authorization.forgot_password.repositories;

import project.rtc.authorization.forgot_password.entities.PasswordResetToken;

public interface ResetPasswordTokenRepository {
	
	PasswordResetToken save(PasswordResetToken resetPasswordToken);
	PasswordResetToken findByToken(String token);
	int removeByToken(String token);

}
