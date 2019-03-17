package com.kotlart.lingver.service.respository;

import com.kotlart.lingver.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByAuthority(String authority);
}
