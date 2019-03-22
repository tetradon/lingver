package com.kotlart.lingver.service.respository;

import com.kotlart.lingver.model.entity.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @EntityGraph(value = Profile.ENTITY_GRAPH, type = EntityGraph.EntityGraphType.LOAD)
    Profile findByUsername(String username);
}

