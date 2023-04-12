package project.rtc.authorization.basic_login.controllers.mvc;

import org.springframework.web.bind.annotation.GetMapping;

public interface AuthenticationPageController {
    @GetMapping(path = "/app/login")
    String get();
}
