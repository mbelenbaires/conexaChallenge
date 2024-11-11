package com.example.conexa_challenge.dto;

import com.example.conexa_challenge.validator.annotations.MatchingPasswords;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@MatchingPasswords
public class UserDto {
    @NotNull
    @NotEmpty
    private String user;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String matchingPassword;
}
