package com.kotlart.lingver.service;

import com.kotlart.lingver.model.Profile;
import com.kotlart.lingver.model.Role;
import com.kotlart.lingver.respository.ProfileRepository;
import com.kotlart.lingver.respository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class LingverUserDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public LingverUserDetailsService(ProfileRepository profileRepository, RoleRepository roleRepository) {
        this.profileRepository = profileRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("name is empty");
        }
        return profileRepository.findByUsername(username);

    }

    @PostConstruct
    public void addUser() {
        if (profileRepository.findByUsername("user") == null) {
            List<Role> authorities = new ArrayList<>();
            final Role role = roleRepository.findByAuthority(Role.USER);
            authorities.add(role);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            final Profile user = Profile.builder()
                    .username("user")
                    .password(encoder.encode("pass"))
                    .authorities(authorities)
                    .enabled(true)
                    .build();
            profileRepository.save(user);
        }
    }

}
