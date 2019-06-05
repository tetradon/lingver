package com.kotlart.lingver.service;

import com.kotlart.lingver.model.entity.Profile;
import com.kotlart.lingver.model.entity.Role;
import com.kotlart.lingver.service.impl.ProfileServiceBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProfileServiceBeanTest extends AbstractServiceBeanTest {

    private ProfileServiceBean sut;

    @Before
    public void before() {
        sut = new ProfileServiceBean(super.profileRepository, super.roleRepository, new BCryptPasswordEncoder());
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
        Profile found = super.profileRepository.findById(persisted.getId()).orElse(null);
        Assert.assertNotNull(found);
        Assert.assertEquals(found.getUsername(), profile.getUsername());
        Assert.assertEquals(found.getEmail(), profile.getEmail());
        Assert.assertNotEquals(found.getPassword(), rawPassword);
        Assert.assertTrue(found.isEnabled());
        Assert.assertEquals(1, found.getAuthorities().size());
        Assert.assertEquals(Role.USER, found.getAuthorities().get(0).getAuthority());
    }

    @Test
    public void test_loadUserByUsername() {
        Role role = super.roleRepository.findByAuthority(Role.USER);
        String usernameOfProfileToLoad = "test";
        Profile profile = Profile.builder()
                .username(usernameOfProfileToLoad)
                .email("test")
                .password("test")
                .authorities(Collections.singletonList(role)).build();
        super.profileRepository.save(profile);

        Assert.assertNotNull(sut.loadUserByUsername(usernameOfProfileToLoad));
    }
}
