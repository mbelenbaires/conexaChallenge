package com.example.conexa_challenge.service;

import com.example.conexa_challenge.dto.BuildRequestDto;

public interface BuildRequestService {
    BuildRequestDto buildGetAllByPageRequest(Integer pageNumber, Integer pageSize, String entity);

    BuildRequestDto buildGetEntityByIdRequest(String entity);

    BuildRequestDto buildGetEntityByParamRequest(String entity, String paramName, String paramValue);
}
