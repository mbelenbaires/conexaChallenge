package com.example.conexa_challenge.service.impl;

import com.example.conexa_challenge.dto.UserDto;
import com.example.conexa_challenge.service.RegistrationService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public String registerNewUser(UserDto userDto) {
        return userDto.getUser();
    }
}
