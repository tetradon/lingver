package com.kotlart.lingver.rest;

import com.kotlart.lingver.config.security.LingverUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping
    String user(Principal principal) {
        if (principal != null) {
            LingverUserDetails activeUser = (LingverUserDetails) ((Authentication) principal).getPrincipal();
            return activeUser.getUsername() + " " + activeUser.getAuthorities() + " " + activeUser.getPassword();
        } else {
            return "no active user";
        }
    }
}
