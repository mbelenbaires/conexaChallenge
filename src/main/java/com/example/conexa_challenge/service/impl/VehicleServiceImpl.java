package com.example.conexa_challenge.service.impl;

import com.example.conexa_challenge.dto.BuildRequestDto;
import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetPagesResponseDto;
import com.example.conexa_challenge.entity.Person;
import com.example.conexa_challenge.entity.Vehicle;
import com.example.conexa_challenge.service.BuildRequestService;
import com.example.conexa_challenge.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final BuildRequestService buildRequestService;

    private final RestTemplate restTemplate;

    private final String VEHICLES_URL = "Vehicles";

    @Override
    public GetPagesResponseDto<Vehicle> getVehiclesByPage(Integer pageNumber, Integer pageSize) {
        BuildRequestDto request = buildRequestService.buildGetAllByPageRequest(pageNumber, pageSize, VEHICLES_URL);

        return restTemplate.exchange(request.getUrl(), HttpMethod.GET, request.getRequestEntity(), new ParameterizedTypeReference<GetPagesResponseDto<Vehicle>>() {
        }, request.getParams()).getBody();
    }

    @Override
    public GetByIdResponseWrapperDto<Vehicle> getVehicleById(Integer id) {
        BuildRequestDto request = buildRequestService.buildGetEntityByIdRequest(VEHICLES_URL);
        try {
            return restTemplate.exchange(request.getUrl() + "/{id}", HttpMethod.GET, request.getRequestEntity(), new ParameterizedTypeReference<GetByIdResponseWrapperDto<Vehicle>>() {
            }, id).getBody();
        } catch (
                HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new GetByIdResponseWrapperDto<>("El vehículo no pudo ser encontrado", null);
            }
            throw e;
        }

    }

    @Override
    public GetByParamResponseWrapperDto<Vehicle> getVehiclesByName(String name) {
        BuildRequestDto request = buildRequestService.buildGetEntityByParamRequest(VEHICLES_URL, "name", name);
        return restTemplate.exchange(request.getUrl(), HttpMethod.GET, request.getRequestEntity(), new ParameterizedTypeReference<GetByParamResponseWrapperDto<Vehicle>>() {
        }, request.getParams()).getBody();
    }
}
