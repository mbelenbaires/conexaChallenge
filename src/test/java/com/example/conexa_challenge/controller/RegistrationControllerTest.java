package com.example.conexa_challenge.controller;

import com.example.conexa_challenge.dto.UserDto;
import com.example.conexa_challenge.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest {
    @Mock
    private RegistrationService registrationService;

    @InjectMocks
    private RegistrationController registrationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void registerNewUser_ShouldReturnCreated_WhenValidUserDtoIsPassed() throws Exception {
        mockMvc.perform(post("/registration")
                        .contentType("application/json")
                        .content("{\"user\": \"newUser\", \"password\": \"password123\", \"matchingPassword\": \"password123\"}"))
                .andExpect(status().isCreated());

        verify(registrationService, times(1)).registerNewUser(any());
    }

    @Test
    void registerNewUser_ShouldReturnBadRequest_WhenUserDtoIsInvalid() throws Exception {
        String invalidJson = "{\"name\": \"\", \"password\": \"\"}";

        mockMvc.perform(post("/registration")
                        .contentType("application/json")
                        .content(invalidJson))
                .andExpect(status().isBadRequest());

        verify(registrationService, never()).registerNewUser(any(UserDto.class));
    }
}
