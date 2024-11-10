package com.example.conexa_challenge.service;

import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetPagesResponseDto;
import com.example.conexa_challenge.entity.Starship;

public interface StarshipService {
    GetPagesResponseDto<Starship> getStarshipsByPage(Integer pageNumber, Integer pageSize);

    GetByIdResponseWrapperDto<Starship> getStarshipById(Integer id);

    GetByParamResponseWrapperDto<Starship> getStarshipsByName(String name);
}
