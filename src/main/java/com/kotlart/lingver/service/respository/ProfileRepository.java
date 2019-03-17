package com.kotlart.lingver.service.respository;

import com.kotlart.lingver.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUsername(String username);
}

