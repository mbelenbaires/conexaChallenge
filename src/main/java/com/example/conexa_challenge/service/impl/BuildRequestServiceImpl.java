package com.example.conexa_challenge.service.impl;

import com.example.conexa_challenge.dto.BuildRequestDto;
import com.example.conexa_challenge.service.BuildRequestService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BuildRequestServiceImpl implements BuildRequestService {

    @Value("${swapi.base.url}")
    private String swapiBaseUrl;


    @Override
    public BuildRequestDto buildGetAllByPageRequest(Integer pageNumber, Integer pageSize, String entity) {
        BuildRequestDto buildParameters = new BuildRequestDto();

        var requestEntity = new HttpEntity<>(createHeaders());

        String url = swapiBaseUrl + entity;
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .replaceQueryParam("page", "{page}")
                .replaceQueryParam("limit", "{limit}")
                .encode()
                .toUriString();

        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(pageNumber));
        params.put("limit", String.valueOf(pageSize));

        buildParameters.setUrl(urlTemplate);
        buildParameters.setParams(params);
        buildParameters.setRequestEntity(requestEntity);

        return buildParameters;
    }

    @Override
    public BuildRequestDto buildGetEntityByIdRequest(String entity) {
        BuildRequestDto buildParameters = new BuildRequestDto();
        buildParameters.setRequestEntity(new HttpEntity<>(createHeaders()));
        buildParameters.setUrl(swapiBaseUrl + entity);
        return buildParameters;
    }

    @Override
    public BuildRequestDto buildGetEntityByParamRequest(String entity, String paramName, String paramValue) {
        BuildRequestDto buildParameters = new BuildRequestDto();

        var requestEntity = new HttpEntity<>(createHeaders());

        String url = swapiBaseUrl + entity;
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .replaceQueryParam(paramName, paramValue)
                .encode()
                .toUriString();

        Map<String, String> params = new HashMap<>();
        params.put(paramName, paramValue);

        buildParameters.setUrl(urlTemplate);
        buildParameters.setParams(params);
        buildParameters.setRequestEntity(requestEntity);

        return buildParameters;
    }

    private HttpHeaders createHeaders() {
        var requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("User-Agent", "ConexaChallenge/1.0");
        return requestHeaders;
    }
}
