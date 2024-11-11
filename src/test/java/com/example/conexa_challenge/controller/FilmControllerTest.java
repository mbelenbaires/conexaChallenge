package com.example.conexa_challenge.controller;

import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetFilmsResponseWrapperDto;
import com.example.conexa_challenge.dto.ResultDto;
import com.example.conexa_challenge.entity.Film;
import com.example.conexa_challenge.service.FilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class FilmControllerTest {
    @Mock
    private FilmService filmService;

    @InjectMocks
    private FilmController filmController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(filmController).build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetFilms() throws Exception {
        GetFilmsResponseWrapperDto mockResponse = new GetFilmsResponseWrapperDto();
        when(filmService.getFilms()).thenReturn(mockResponse);

        mockMvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(filmService, times(1)).getFilms();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetFilmById_Success() throws Exception {
        Integer filmId = 999;

        when(filmService.getFilmById(filmId)).thenReturn(new GetByIdResponseWrapperDto<>("ok", new ResultDto<>()));

        mockMvc.perform(get("/films/{id}", filmId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("ok"))
                .andExpect(jsonPath("$.result").exists());

        verify(filmService, times(1)).getFilmById(filmId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetFilmById_NotFound() throws Exception {
        Integer filmId = 999;

        when(filmService.getFilmById(filmId)).thenReturn(new GetByIdResponseWrapperDto<>("La película no pudo ser encontrada", null));

        mockMvc.perform(get("/films/{id}", filmId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("La película no pudo ser encontrada"));

        verify(filmService, times(1)).getFilmById(filmId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetFilmsByTitle() throws Exception {
        String title = "Star Wars";
        GetByParamResponseWrapperDto<Film> mockResponse = new GetByParamResponseWrapperDto<>();
        when(filmService.getFilmsByTitle(title)).thenReturn(mockResponse);

        mockMvc.perform(get("/films/getByTitle").param("title", title))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        verify(filmService, times(1)).getFilmsByTitle(title);
    }
}
