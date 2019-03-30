package com.kotlart.lingver.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Table(name = Profile.TABLE_NAME)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@NamedEntityGraph(name = Profile.ENTITY_GRAPH,
        attributeNodes = @NamedAttributeNode(value = "authorities"))
public class Profile extends AbstractEntity implements UserDetails {
    public static final String TABLE_NAME = "profile";
    public static final String ENTITY_GRAPH = TABLE_NAME + ".detail";

    @Id
    @Column(name = TABLE_NAME + PK_SUFFIX)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String firstName;
    private String lastName;

    private boolean locked;
    private boolean enabled;

    @Temporal(TemporalType.DATE)
    private Date accountExpirationDate;

    @Temporal(TemporalType.DATE)
    private Date credentialExpirationDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "profile_role",
            joinColumns = {@JoinColumn(name = "profile_fk")},
            inverseJoinColumns = {@JoinColumn(name = "role_fk")}
    )
    @NotEmpty
    private List<Role> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return accountExpirationDate == null || accountExpirationDate.after(new Date());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialExpirationDate == null || credentialExpirationDate.after(new Date());
    }
}
