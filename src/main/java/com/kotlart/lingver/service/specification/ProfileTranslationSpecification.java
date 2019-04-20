package com.kotlart.lingver.service.specification;

import com.kotlart.lingver.model.entity.Profile;
import com.kotlart.lingver.model.entity.ProfileTranslation;
import com.kotlart.lingver.model.entity.ProfileTranslation_;
import com.kotlart.lingver.model.entity.Profile_;
import com.kotlart.lingver.model.entity.Translation_;
import com.kotlart.lingver.model.entity.Word_;
import org.springframework.data.jpa.domain.Specification;

public class ProfileTranslationSpecification {
    public static Specification<ProfileTranslation> withProfileId(Long id) {
        return (Specification<ProfileTranslation>) (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get(ProfileTranslation_.profile).get(Profile_.id), id);
    }


    public static Specification<ProfileTranslation> withWordValueLike(String word) {
        return (Specification<ProfileTranslation>) (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get(ProfileTranslation_.translation).get(Translation_.word).get(Word_.value),
                word + "%");
    }

    public static Specification<ProfileTranslation> withTranslationValueLike(String translationValue) {
        return (Specification<ProfileTranslation>) (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get(ProfileTranslation_.translation).get(Translation_.value),
                translationValue + "%");
    }

    public static Specification<ProfileTranslation> withProfileAndSearch(Profile profile, String search) {
        return withProfileId(profile.getId()).and(withWordValueLike(search).or(withTranslationValueLike(search)));
    }
}


