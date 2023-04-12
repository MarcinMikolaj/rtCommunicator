package project.rtc.registration.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public interface RegistrationController {

    @GetMapping(path = "/app/register")
    String getRegisterPage();

}
