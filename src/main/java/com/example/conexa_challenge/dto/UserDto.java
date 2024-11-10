package com.example.conexa_challenge.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDto {
    @NotNull
    @NotEmpty
    private String user;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;
}
