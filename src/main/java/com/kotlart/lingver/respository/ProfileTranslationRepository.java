package com.kotlart.lingver.respository;

import com.kotlart.lingver.model.ProfileTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileTranslationRepository extends JpaRepository<ProfileTranslation, Long> {
}
