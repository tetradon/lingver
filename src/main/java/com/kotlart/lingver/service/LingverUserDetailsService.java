package com.kotlart.lingver.service;

import com.kotlart.lingver.model.Role;
import com.kotlart.lingver.model.User;
import com.kotlart.lingver.respository.RoleRepository;
import com.kotlart.lingver.respository.UserRepository;
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

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public LingverUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("name is empty");
        }
        return userRepository.findByUsername(username);

    }

    @PostConstruct
    public void addUser() {
        List<Role> authorities = new ArrayList<>();
        final Role role = roleRepository.findByAuthority("USER");
        authorities.add(role);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final User user = User.builder()
                .username("user1")
                .password(encoder.encode("pass"))
                .authorities(authorities)
                .accountNonLocked(true)
                .enabled(true)
                .build();
        userRepository.save(user);
    }

}
