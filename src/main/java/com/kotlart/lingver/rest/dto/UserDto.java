package com.kotlart.lingver.rest.dto;

import com.kotlart.lingver.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<Role> authorities;
}
