package project.rtc.authorization.forgot_password.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/app/forgot")
public interface ForgotPasswordController  {
    @GetMapping(path = "/")
    String get();

    @GetMapping(path = "/password/tk")
    String handleChangingPassword();

}
