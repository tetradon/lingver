package com.kotlart.lingver.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = Role.TABLE_NAME)
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AbstractEntity implements GrantedAuthority {
    public static final String TABLE_NAME = "role";
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    @Id
    @Column(name = TABLE_NAME + PK_SUFFIX)
    private Long id;

    @Column(name = "name")
    private String authority;
}
