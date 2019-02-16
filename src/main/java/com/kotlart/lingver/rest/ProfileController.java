package com.kotlart.lingver.rest;

import com.kotlart.lingver.model.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("profile")
public class ProfileController {

    @GetMapping
    String user(Authentication authentication) {
        if (authentication != null) {
            Profile activeUser = (Profile) authentication.getPrincipal();
            return activeUser.getUsername() + " " + activeUser.getAuthorities() + " " + activeUser.getPassword();
        } else {
            return "no active profile";
        }
    }
}
