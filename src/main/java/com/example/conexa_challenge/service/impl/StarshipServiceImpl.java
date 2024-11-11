package com.example.conexa_challenge.service.impl;

import com.example.conexa_challenge.dto.BuildRequestDto;
import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetPagesResponseDto;
import com.example.conexa_challenge.entity.Person;
import com.example.conexa_challenge.entity.Starship;
import com.example.conexa_challenge.service.BuildRequestService;
import com.example.conexa_challenge.service.StarshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class StarshipServiceImpl implements StarshipService {
    private final BuildRequestService buildRequestService;

    private final RestTemplate restTemplate;

    private final String STARSHIPS_URL = "starships";

    @Override
    public GetPagesResponseDto<Starship> getStarshipsByPage(Integer pageNumber, Integer pageSize) {
        BuildRequestDto request = buildRequestService.buildGetAllByPageRequest(pageNumber, pageSize, STARSHIPS_URL);

        return restTemplate.exchange(request.getUrl(), HttpMethod.GET, request.getRequestEntity(), new ParameterizedTypeReference<GetPagesResponseDto<Starship>>() {
        }, request.getParams()).getBody();
    }

    @Override
    public GetByIdResponseWrapperDto<Starship> getStarshipById(Integer id) {
        BuildRequestDto request = buildRequestService.buildGetEntityByIdRequest(STARSHIPS_URL);
        try {
            return restTemplate.exchange(request.getUrl() + "/{id}", HttpMethod.GET, request.getRequestEntity(), new ParameterizedTypeReference<GetByIdResponseWrapperDto<Starship>>() {
            }, id).getBody();
        } catch (
                HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new GetByIdResponseWrapperDto<>("La nave estelar no pudo ser encontrada", null);
            }
            throw e;
        }

    }

    @Override
    public GetByParamResponseWrapperDto<Starship> getStarshipsByName(String name) {
        BuildRequestDto request = buildRequestService.buildGetEntityByParamRequest(STARSHIPS_URL, "name", name);
        return restTemplate.exchange(request.getUrl(), HttpMethod.GET, request.getRequestEntity(), new ParameterizedTypeReference<GetByParamResponseWrapperDto<Starship>>() {
        }, request.getParams()).getBody();
    }
}
