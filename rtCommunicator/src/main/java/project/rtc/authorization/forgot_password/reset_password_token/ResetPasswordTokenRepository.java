package project.rtc.authorization.forgot_password.reset_password_token;

public interface ResetPasswordTokenRepository {
	
	public PasswordResetToken save(PasswordResetToken resetPasswordToken);
	public PasswordResetToken findByToken(String token);
	public int removeByToken(String token);

}
