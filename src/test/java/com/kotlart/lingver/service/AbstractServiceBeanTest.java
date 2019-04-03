package com.kotlart.lingver.service;

import com.kotlart.lingver.model.entity.Profile;
import com.kotlart.lingver.model.entity.Role;
import com.kotlart.lingver.service.respository.ProfileRepository;
import com.kotlart.lingver.service.respository.RoleRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.Collections;

@RunWith(SpringRunner.class)
@DataJpaTest
public abstract class AbstractServiceBeanTest {

    protected Profile DEFAULT_PROFILE;
    protected Role DEFAULT_USER_ROLE;

    @Autowired
    protected ProfileRepository profileRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @PostConstruct
    public void postConstruct() {
        DEFAULT_USER_ROLE = roleRepository.save(Role.builder().id(1L).authority(Role.USER).build());
        DEFAULT_PROFILE = profileRepository.save(Profile.builder().username("default").email("default").password("default").authorities(Collections.singletonList(DEFAULT_USER_ROLE)).build());
    }
}
