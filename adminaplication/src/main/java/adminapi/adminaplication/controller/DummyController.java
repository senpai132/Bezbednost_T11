package adminapi.adminaplication.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/dummy")
public class DummyController {

    @GetMapping("/test")
    public String confirmRegistration() {
        return "Test bezbednosti";
    }
}
