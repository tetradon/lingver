package com.kotlart.lingver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Table(name = User.TABLE_NAME)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends AbstractEntity implements UserDetails {
    public static final String TABLE_NAME = "user_detail";
    @Id
    @Column(name = TABLE_NAME + PK_SUFFIX)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean accountNonLocked;
    private boolean enabled;

    @Temporal(TemporalType.DATE)
    private Date accountExpirationDate;

    @Temporal(TemporalType.DATE)
    private Date credentialExpirationDate;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_fk")},
            inverseJoinColumns = {@JoinColumn(name = "role_fk")}
    )
    private List<Role> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return accountExpirationDate == null || accountExpirationDate.after(new Date());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialExpirationDate == null || credentialExpirationDate.after(new Date());
    }
}
