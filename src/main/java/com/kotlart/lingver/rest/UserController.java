package com.kotlart.lingver.rest;

import com.kotlart.lingver.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping
    String user(Authentication authentication) {
        if (authentication != null) {
            User activeUser = (User) authentication.getPrincipal();
            return activeUser.getUsername() + " " + activeUser.getAuthorities() + " " + activeUser.getPassword();
        } else {
            return "no active user";
        }
    }
}
