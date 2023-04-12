package project.rtc.registration.controllers.mvc;

import org.springframework.web.bind.annotation.GetMapping;

public interface RegistrationController {

    @GetMapping(path = "/app/register")
    String getRegisterPage();

}
