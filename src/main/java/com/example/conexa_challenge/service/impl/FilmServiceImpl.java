package com.example.conexa_challenge.service.impl;

import com.example.conexa_challenge.dto.BuildRequestDto;
import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetFilmsResponseWrapperDto;
import com.example.conexa_challenge.entity.Film;
import com.example.conexa_challenge.service.BuildRequestService;
import com.example.conexa_challenge.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final BuildRequestService buildRequestService;

    private final RestTemplate restTemplate;

    @Value("${swapi.base.url}")
    private String swapiBaseUrl;

    private final String FILMS_URL = "films";

    @Override
    public GetFilmsResponseWrapperDto getFilms() {
        var requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("User-Agent", "ConexaChallenge/1.0");
        var requestEntity = new HttpEntity<>(requestHeaders);

        String url = swapiBaseUrl + FILMS_URL;
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .encode()
                .toUriString();

        return restTemplate.exchange(urlTemplate, HttpMethod.GET, requestEntity, GetFilmsResponseWrapperDto.class).getBody();
    }

    @Override
    public GetByIdResponseWrapperDto<Film> getFilmById(Integer id) {
        BuildRequestDto request = buildRequestService.buildGetEntityByIdRequest(FILMS_URL);
        try {
            return restTemplate.exchange(request.getUrl() + "/{id}", HttpMethod.GET, request.getRequestEntity(), new ParameterizedTypeReference<GetByIdResponseWrapperDto<Film>>() {
            }, id).getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new GetByIdResponseWrapperDto<>("La película no pudo ser encontrada", null);
            }
            throw e;
        }

    }

    @Override
    public GetByParamResponseWrapperDto<Film> getFilmsByTitle(String title) {
        BuildRequestDto request = buildRequestService.buildGetEntityByParamRequest(FILMS_URL, "title", title);
        return restTemplate.exchange(request.getUrl(), HttpMethod.GET, request.getRequestEntity(), new ParameterizedTypeReference<GetByParamResponseWrapperDto<Film>>() {
        }, request.getParams()).getBody();
    }
}
