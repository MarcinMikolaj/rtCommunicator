package project.rtc.authorization.forgot_password.services;

import project.rtc.authorization.forgot_password.entities.PasswordResetToken;
import project.rtc.infrastructure.exception.exceptions.InvalidTokenException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

public interface ForgotPasswordService {

    // This method is called to begin the password change process for the user.
    // Allows you to assign a token for a given account for which the password is to be updated.
    // And it sends to the user's e-mail address a link redirecting to enter a new password.
    PasswordResetToken startTheProcessOfChangingThePassword(String email, HttpServletResponse response) throws MessagingException;

    // If the token provided by the user is correct (i.e. it matches the token assigned to the user and has not expired)
    // , the password change operation is started.
    // If the token is incorrect, it stops the password change process
    PasswordResetToken changePasswordIfTokenIsCorrect(String tokenFromUrl, String password) throws InvalidTokenException;

}
