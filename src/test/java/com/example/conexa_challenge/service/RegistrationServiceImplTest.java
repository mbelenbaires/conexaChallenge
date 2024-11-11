package com.example.conexa_challenge.service;

import com.example.conexa_challenge.dto.UserDto;
import com.example.conexa_challenge.service.impl.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceImplTest {

    @InjectMocks
    RegistrationServiceImpl registrationService;

    @Test
    void registerNewUser_ShouldReturnUserName_WhenUserDtoIsProvided() {
        UserDto userDto = mock(UserDto.class);
        String expectedUserName = "name";
        when(userDto.getUser()).thenReturn(expectedUserName);

        String actualUserName = registrationService.registerNewUser(userDto);

        assertNotNull(actualUserName);
        assertEquals(expectedUserName, actualUserName);
    }
}
