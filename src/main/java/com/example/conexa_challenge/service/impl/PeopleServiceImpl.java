package com.example.conexa_challenge.service.impl;

import com.example.conexa_challenge.dto.BuildRequestDto;
import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetPagesResponseDto;
import com.example.conexa_challenge.entity.Film;
import com.example.conexa_challenge.entity.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.example.conexa_challenge.service.PeopleService;

@Service
@RequiredArgsConstructor
public class PeopleServiceImpl implements PeopleService {

    private final BuildRequestServiceImpl buildRequestService;

    private final RestTemplate restTemplate;

    String PEOPLE_URL = "people";

    @Override
    public GetPagesResponseDto<Person> getPeopleByPage(Integer pageNumber, Integer pageSize) {
        BuildRequestDto request = buildRequestService.buildGetAllByPageRequest(pageNumber, pageSize, PEOPLE_URL);

        return restTemplate.exchange(request.getUrl(), HttpMethod.GET, request.getRequestEntity(), new ParameterizedTypeReference<GetPagesResponseDto<Person>>() {
        }, request.getParams()).getBody();
    }

    @Override
    public GetByIdResponseWrapperDto<Person> getPersonById(Integer id) {
        BuildRequestDto request = buildRequestService.buildGetEntityByIdRequest(PEOPLE_URL);
        try {
            return restTemplate.exchange(request.getUrl() + "/{id}", HttpMethod.GET, request.getRequestEntity(), new ParameterizedTypeReference<GetByIdResponseWrapperDto<Person>>() {
            }, id).getBody();
        } catch (
                HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new GetByIdResponseWrapperDto<>("La persona no pudo ser encontrada", null);
            }
            throw e;
        }

    }

    @Override
    public GetByParamResponseWrapperDto<Person> getPeopleByName(String name) {
        BuildRequestDto request = buildRequestService.buildGetEntityByParamRequest(PEOPLE_URL, "name", name);
        return restTemplate.exchange(request.getUrl(), HttpMethod.GET, request.getRequestEntity(), new ParameterizedTypeReference<GetByParamResponseWrapperDto<Person>>() {
        }, request.getParams()).getBody();
    }
}
