package com.example.conexa_challenge.service;

import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetFilmsResponseWrapperDto;
import com.example.conexa_challenge.entity.Film;

public interface FilmService {
    GetFilmsResponseWrapperDto getFilms();

    GetByIdResponseWrapperDto<Film> getFilmById(Integer id);

    GetByParamResponseWrapperDto<Film> getFilmsByTitle(String title);
}
