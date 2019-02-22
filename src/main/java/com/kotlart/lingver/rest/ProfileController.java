package com.kotlart.lingver.rest;

import com.kotlart.lingver.model.Profile;
import com.kotlart.lingver.rest.dto.MessageDto;
import com.kotlart.lingver.rest.dto.ProfileDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("profile")
public class ProfileController {

    private final ModelMapper modelMapper;

    @Autowired
    public ProfileController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping
    ResponseEntity user(Authentication authentication) {

        ResponseEntity response;
        if (authentication != null) {
            Profile activeUser = (Profile) authentication.getPrincipal();
            ProfileDto profileDto = modelMapper.map(activeUser, ProfileDto.class);
            response = ResponseEntity.ok(profileDto);
        } else {
            response = ResponseEntity.ok(new MessageDto("no active user"));
        }
        return response;
    }
}
