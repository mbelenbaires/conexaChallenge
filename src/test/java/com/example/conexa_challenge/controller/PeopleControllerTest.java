package com.example.conexa_challenge.controller;

import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetPagesResponseDto;
import com.example.conexa_challenge.dto.ResultDto;
import com.example.conexa_challenge.entity.Person;
import com.example.conexa_challenge.entity.Vehicle;
import com.example.conexa_challenge.service.PeopleService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PeopleControllerTest {
    @InjectMocks
    private PeopleController peopleController;

    @Mock
    private PeopleService peopleService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(peopleController).build();
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetPeopleByPage() throws Exception {
        GetPagesResponseDto<Person> mockResponse = new GetPagesResponseDto<>();
        mockResponse.setResults(Collections.singletonList(new Person()));
        when(peopleService.getPeopleByPage(1, 10)).thenReturn(mockResponse);

        mockMvc.perform(get("/people")
                        .param("pageNumber", "1")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results").isArray())
                .andExpect(jsonPath("$.results.length()").value(1));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetPeopleById() throws Exception {
        GetByIdResponseWrapperDto<Person> mockResponse = new GetByIdResponseWrapperDto<>("ok", new ResultDto<>());
        when(peopleService.getPersonById(1)).thenReturn(mockResponse);

        mockMvc.perform(get("/people/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").isNotEmpty());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetPeopleByName() throws Exception {
        GetByParamResponseWrapperDto<Person> mockResponse = new GetByParamResponseWrapperDto<>();
        mockResponse.setMessage("ok");
        mockResponse.setResult(Collections.singletonList(new ResultDto<>()));
        when(peopleService.getPeopleByName("Falcon")).thenReturn(mockResponse);

        mockMvc.perform(get("/people/getByName")
                        .param("name", "Falcon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").isArray())
                .andExpect(jsonPath("$.result.length()").value(1));
    }
}
