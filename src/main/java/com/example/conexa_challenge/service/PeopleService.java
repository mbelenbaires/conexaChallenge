package com.example.conexa_challenge.service;

import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetPagesResponseDto;
import com.example.conexa_challenge.entity.Person;

public interface PeopleService {
    GetPagesResponseDto<Person> getPeopleByPage(Integer pageNumber, Integer pageSize);

    GetByIdResponseWrapperDto<Person> getPersonById(Integer id);

    GetByParamResponseWrapperDto<Person> getPeopleByName(String name);
}
