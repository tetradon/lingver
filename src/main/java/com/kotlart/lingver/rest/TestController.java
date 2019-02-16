package com.kotlart.lingver.rest;

import com.kotlart.lingver.rest.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TestController {
    @GetMapping
    ResponseEntity test() {
        return ResponseEntity.ok(new MessageDto("api ok"));
    }

    @GetMapping
    @RequestMapping("admin")
    ResponseEntity testAdmin() {
        return ResponseEntity.ok(new MessageDto("api ADMIN ok"));
    }
}
