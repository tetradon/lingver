package com.kotlart.lingver.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = User.TABLE_NAME)
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends AbstractEntity {
    public static final String TABLE_NAME = "user";
    @Id
    @Column(name = TABLE_NAME + PK_SUFFIX)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;

    @ManyToOne
    @JoinColumn(name = Role.TABLE_NAME + FK_SUFFIX)
    private Role role;
}
