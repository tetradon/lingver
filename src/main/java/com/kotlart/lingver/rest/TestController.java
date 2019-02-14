package com.kotlart.lingver.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TestController {
    @GetMapping
    String test() {
        return "api OK";
    }

    @GetMapping
    @RequestMapping("admin")
    String testAdmin() {
        return "api admin OK";
    }
}
