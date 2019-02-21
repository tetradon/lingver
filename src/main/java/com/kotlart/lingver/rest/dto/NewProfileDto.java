package com.kotlart.lingver.rest.dto;

import lombok.Data;

@Data
public class NewProfileDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
