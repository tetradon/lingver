package com.kotlart.lingver.service;

import com.kotlart.lingver.model.entity.Profile;
import com.kotlart.lingver.model.entity.Role;
import com.kotlart.lingver.service.impl.ProfileServiceBean;
import com.kotlart.lingver.service.respository.ProfileRepository;
import com.kotlart.lingver.service.respository.RoleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProfileServiceBeanTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RoleRepository roleRepository;

    private ProfileServiceBean sut;

    @Before
    public void before() {
        sut = new ProfileServiceBean(profileRepository, roleRepository, new BCryptPasswordEncoder());
        roleRepository.save(Role.builder().authority(Role.USER).build());
    }

    @Test
    public void test_createProfile() {
        String rawPassword = "password";
        Profile profile = Profile.builder()
                .username("test")
                .email("test")
                .password(rawPassword)
                .build();
        Profile persisted = sut.createProfile(profile);
        Profile found = profileRepository.findById(persisted.getId()).orElse(null);

        Assert.assertEquals(found.getUsername(), profile.getUsername());
        Assert.assertEquals(found.getEmail(), profile.getEmail());
        Assert.assertNotEquals(found.getPassword(), rawPassword);
        Assert.assertTrue(found.isEnabled());
        Assert.assertEquals(1, found.getAuthorities().size());
        Assert.assertEquals(Role.USER, found.getAuthorities().get(0).getAuthority());
    }

    @Test
    public void test_loadUserByUsername() {
        String usernameOfProfileToLoad = "test";
        Profile profile = Profile.builder()
                .username(usernameOfProfileToLoad)
                .email("test")
                .password("test")
                .authorities(Collections.singletonList(Role.builder().authority(Role.USER).build()))
                .build();
        profileRepository.save(profile);

        Assert.assertNotNull(sut.loadUserByUsername(usernameOfProfileToLoad));
    }
}
