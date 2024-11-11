package com.example.conexa_challenge.controller;

import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetPagesResponseDto;
import com.example.conexa_challenge.dto.ResultDto;
import com.example.conexa_challenge.entity.Vehicle;
import com.example.conexa_challenge.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class VehicleControllerTest {
    @InjectMocks
    private VehicleController vehicleController;

    @Mock
    private VehicleService vehicleService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetVehiclesByPage() throws Exception {
        GetPagesResponseDto<Vehicle> mockResponse = new GetPagesResponseDto<>();
        mockResponse.setResults(Collections.singletonList(new Vehicle()));
        when(vehicleService.getVehiclesByPage(1, 10)).thenReturn(mockResponse);

        mockMvc.perform(get("/vehicles")
                        .param("pageNumber", "1")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results").isArray())
                .andExpect(jsonPath("$.results.length()").value(1));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetVehicleById() throws Exception {
        GetByIdResponseWrapperDto<Vehicle> mockResponse = new GetByIdResponseWrapperDto<>("ok", new ResultDto<>());
        when(vehicleService.getVehicleById(1)).thenReturn(mockResponse);

        mockMvc.perform(get("/vehicles/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetVehiclesByName() throws Exception {
        GetByParamResponseWrapperDto<Vehicle> mockResponse = new GetByParamResponseWrapperDto<>();
        mockResponse.setMessage("ok");
        mockResponse.setResult(Collections.singletonList(new ResultDto<>()));
        when(vehicleService.getVehiclesByName("Falcon")).thenReturn(mockResponse);

        mockMvc.perform(get("/vehicles/getByName")
                        .param("name", "Falcon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").isArray())
                .andExpect(jsonPath("$.result.length()").value(1));
    }

}
