package com.kotlart.lingver.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
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
