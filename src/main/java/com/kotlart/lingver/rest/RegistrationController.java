package com.kotlart.lingver.rest;

import com.kotlart.lingver.model.Profile;
import com.kotlart.lingver.rest.dto.NewProfileDto;
import com.kotlart.lingver.rest.dto.ProfileDto;
import com.kotlart.lingver.rest.util.ResponseUtil;
import com.kotlart.lingver.service.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final ModelMapper modelMapper;

    private final ProfileService profileService;

    @Autowired
    public RegistrationController(ModelMapper modelMapper, ProfileService profileService) {
        this.modelMapper = modelMapper;
        this.profileService = profileService;
    }

    @PostMapping
    ResponseEntity register(@Valid @RequestBody NewProfileDto newProfileDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.badRequest(result.getFieldErrors());
        }
        Profile profile = modelMapper.map(newProfileDto, Profile.class);
        final Profile persistedProfile = profileService.createProfile(profile);
        return ResponseEntity.ok().body(modelMapper.map(persistedProfile, ProfileDto.class));
    }
}
