package me.vinuvicho.attemptSeven.controllers;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.registration.RegistrationRequest;
import me.vinuvicho.attemptSeven.registration.RegistrationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/register")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
    @GetMapping(path = "/reject")
    public String reject(@RequestParam("token") String token) {
        return registrationService.rejectToken(token);
    }
}
