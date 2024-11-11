package com.example.conexa_challenge.service;

import com.example.conexa_challenge.dto.BuildRequestDto;
import com.example.conexa_challenge.service.impl.BuildRequestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class BuildRequestServiceImplTest {

    @InjectMocks
    BuildRequestServiceImpl buildRequestService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(buildRequestService, "swapiBaseUrl", "http://mocked-url");
    }

    @Test
    void buildGetAllByPageRequest_ShouldReturnCorrectUrlAndParams() {
        String entity = "films";
        Integer pageNumber = 1;
        Integer pageSize = 10;

        BuildRequestDto result = buildRequestService.buildGetAllByPageRequest(pageNumber, pageSize, entity);

        String expectedUrl = "http://mocked-urlfilms?page={page}&limit={limit}";
        assertEquals(expectedUrl, result.getUrl());

        Map<String, String> params = result.getParams();
        assertEquals("1", params.get("page"));
        assertEquals("10", params.get("limit"));

        HttpEntity<?> requestEntity = result.getRequestEntity();
        assertNotNull(requestEntity);
        HttpHeaders headers = requestEntity.getHeaders();
        assertEquals("application/json", Objects.requireNonNull(headers.getContentType()).toString());
        assertEquals("ConexaChallenge/1.0", Objects.requireNonNull(headers.get("User-Agent")).get(0));
    }

    @Test
    void buildGetEntityByParamRequest_ShouldReturnCorrectUrlAndParams() {
        String entity = "people";
        String paramName = "name";
        String paramValue = "Skywalker";

        BuildRequestDto result = buildRequestService.buildGetEntityByParamRequest(entity, paramName, paramValue);
        String expectedUrl = "http://mocked-urlpeople?name=Skywalker";
        assertEquals(expectedUrl, result.getUrl());

        Map<String, String> params = result.getParams();
        assertEquals(paramValue, params.get(paramName));

        HttpEntity<?> requestEntity = result.getRequestEntity();
        assertNotNull(requestEntity);
        HttpHeaders headers = requestEntity.getHeaders();
        assertEquals("application/json", Objects.requireNonNull(headers.getContentType()).toString());
        assertEquals("ConexaChallenge/1.0", Objects.requireNonNull(headers.get("User-Agent")).get(0));
    }

    @Test
    void buildGetEntityByIdRequest_ShouldReturnCorrectUrlAndHeaders() {
        String entity = "films";

        BuildRequestDto result = buildRequestService.buildGetEntityByIdRequest(entity);

        String expectedUrl = "http://mocked-urlfilms";
        assertEquals(expectedUrl, result.getUrl());

        HttpEntity<?> requestEntity = result.getRequestEntity();
        assertNotNull(requestEntity);
        HttpHeaders headers = requestEntity.getHeaders();
        assertEquals("application/json", Objects.requireNonNull(headers.getContentType()).toString());
        assertEquals("ConexaChallenge/1.0", Objects.requireNonNull(headers.get("User-Agent")).get(0));
    }


}
