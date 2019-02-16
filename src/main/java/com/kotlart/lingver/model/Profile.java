package com.kotlart.lingver.model;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = Profile.TABLE_NAME)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Profile extends AbstractEntity implements UserDetails {
    public static final String TABLE_NAME = "profile";
    @Id
    @Column(name = TABLE_NAME + PK_SUFFIX)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean locked;
    private boolean enabled;

    @Temporal(TemporalType.DATE)
    private Date accountExpirationDate;

    @Temporal(TemporalType.DATE)
    private Date credentialExpirationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "profile_role",
            joinColumns = {@JoinColumn(name = "profile_fk")},
            inverseJoinColumns = {@JoinColumn(name = "role_fk")}
    )
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
