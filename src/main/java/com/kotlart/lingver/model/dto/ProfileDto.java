package com.kotlart.lingver.model.dto;

import com.kotlart.lingver.model.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class ProfileDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<Role> authorities;
}
