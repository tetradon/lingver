package com.kotlart.lingver.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = Role.TABLE_NAME)
@Data
@EqualsAndHashCode(callSuper = false)
public class Role extends AbstractEntity implements GrantedAuthority {
    public static final String TABLE_NAME = "role";
    @Id
    @Column(name = TABLE_NAME + PK_SUFFIX)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String authority;
}
