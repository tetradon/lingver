package com.kotlart.lingver.service.impl;

import com.kotlart.lingver.model.entity.Profile;
import com.kotlart.lingver.model.entity.Role;
import com.kotlart.lingver.service.ProfileService;
import com.kotlart.lingver.service.respository.ProfileRepository;
import com.kotlart.lingver.service.respository.RoleRepository;
import com.kotlart.lingver.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ProfileServiceBean implements UserDetailsService, ProfileService {

    private final ProfileRepository profileRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder encoder;

    @Autowired
    public ProfileServiceBean(ProfileRepository profileRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder) {
        this.profileRepository = profileRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Profile user = profileRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("name is empty");
        }
        return user;
    }

    @Override
    public Profile createProfile(Profile profile) {
        profile.setEnabled(true);
        profile.setPassword(encoder.encode(profile.getPassword()));
        Role role = roleRepository.findByAuthority(Role.USER);
        profile.setAuthorities(Collections.singletonList(role));
        try {
            return profileRepository.save(profile);
        } catch (DataIntegrityViolationException exception) {
            ExceptionUtil.handleDataIntegrityViolationException(exception);
        }
        return null;
    }
}
