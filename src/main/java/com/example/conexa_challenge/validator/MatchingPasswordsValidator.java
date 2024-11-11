package com.example.conexa_challenge.validator;

import com.example.conexa_challenge.dto.UserDto;
import com.example.conexa_challenge.validator.annotations.MatchingPasswords;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class MatchingPasswordsValidator implements ConstraintValidator<MatchingPasswords, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserDto user = (UserDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
