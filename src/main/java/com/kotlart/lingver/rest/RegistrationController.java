package com.kotlart.lingver.rest;

import com.kotlart.lingver.model.Profile;
import com.kotlart.lingver.model.Role;
import com.kotlart.lingver.respository.ProfileRepository;
import com.kotlart.lingver.respository.RoleRepository;
import com.kotlart.lingver.rest.dto.NewProfileDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping
    ResponseEntity user(@RequestBody NewProfileDto newProfileDto) {
        System.out.println(newProfileDto);
        Profile profile = modelMapper.map(newProfileDto, Profile.class);
        profile.setEnabled(true);
        profile.setPassword(new BCryptPasswordEncoder().encode(newProfileDto.getPassword()));
        Role role = roleRepository.findByAuthority(Role.USER);
        profile.setAuthorities(new ArrayList<>(Arrays.asList(role)));
        profileRepository.save(profile);
        return ResponseEntity.ok().build();
    }
}
