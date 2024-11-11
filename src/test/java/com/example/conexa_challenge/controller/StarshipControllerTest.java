package com.example.conexa_challenge.controller;

import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetPagesResponseDto;
import com.example.conexa_challenge.dto.ResultDto;
import com.example.conexa_challenge.entity.Starship;
import com.example.conexa_challenge.service.StarshipService;
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
public class StarshipControllerTest {
    @Mock
    private StarshipService starshipService;

    @InjectMocks
    private StarshipController starshipController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(starshipController).build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetStarshipByPage() throws Exception {
        GetPagesResponseDto<Starship> starshipPageResponse = new GetPagesResponseDto<>();
        starshipPageResponse.setMessage("ok");
        starshipPageResponse.setResults(Collections.singletonList(new Starship()));

        when(starshipService.getStarshipsByPage(1, 10)).thenReturn(starshipPageResponse);

        mockMvc.perform(get("/starships")
                        .param("pageNumber", "1")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results").isArray())
                .andExpect(jsonPath("$.results.length()").value(1));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetStarshipById() throws Exception {
        GetByIdResponseWrapperDto<Starship> starshipResponse = new GetByIdResponseWrapperDto<>("ok", new ResultDto<>());
        when(starshipService.getStarshipById(1)).thenReturn(starshipResponse);

        mockMvc.perform(get("/starships/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetStarshipByName() throws Exception {
        GetByParamResponseWrapperDto<Starship> mockResponse = new GetByParamResponseWrapperDto<>();
        mockResponse.setMessage("ok");
        mockResponse.setResult(Collections.singletonList(new ResultDto<>()));

        when(starshipService.getStarshipsByName("X-Wing")).thenReturn(mockResponse);

        mockMvc.perform(get("/starships/getByName")
                        .param("name", "X-Wing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").isArray())
                .andExpect(jsonPath("$.result.length()").value(1));
    }
}
