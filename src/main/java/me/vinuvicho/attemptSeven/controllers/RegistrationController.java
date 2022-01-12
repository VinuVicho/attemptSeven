package me.vinuvicho.attemptSeven.controllers;

import lombok.AllArgsConstructor;
import me.vinuvicho.attemptSeven.registration.RegistrationRequest;
import me.vinuvicho.attemptSeven.registration.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller                     //to send html
//@RestController               //if I want to send not html
@RequestMapping(path = "/register")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping()
    @ResponseBody               //to send non-html
    public String register(@ModelAttribute RegistrationRequest request) {       //@ModelAttribute isn't necessary
        return registrationService.register(request);
    }

    @GetMapping()
    public String registerPage(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "pages/user/register";
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
