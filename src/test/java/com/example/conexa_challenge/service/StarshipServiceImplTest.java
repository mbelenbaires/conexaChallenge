package com.example.conexa_challenge.service;

import com.example.conexa_challenge.dto.BuildRequestDto;
import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetPagesResponseDto;
import com.example.conexa_challenge.dto.ResultDto;
import com.example.conexa_challenge.entity.Starship;
import com.example.conexa_challenge.service.impl.BuildRequestServiceImpl;
import com.example.conexa_challenge.service.impl.StarshipServiceImpl;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StarshipServiceImplTest {
    @InjectMocks
    private StarshipServiceImpl starshipService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private BuildRequestServiceImpl buildRequestService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(buildRequestService, "swapiBaseUrl", "http://mocked-url");
    }

    @Test
    void getStarships_ShouldReturnStarships_WhenSuccessful() {
        GetPagesResponseDto<Starship> expectedResponse = new GetPagesResponseDto<>();
        expectedResponse.setMessage("ok");
        expectedResponse.setResults(Collections.singletonList(new Starship()));

        BuildRequestDto requestDto = mockRequestGetAllPaged();

        when(buildRequestService.buildGetAllByPageRequest(1, 1, "starships")).thenReturn(requestDto);
        when(restTemplate.exchange("http://mocked-url", HttpMethod.GET, requestDto.getRequestEntity(),
                new ParameterizedTypeReference<GetPagesResponseDto<Starship>>() {
                }, requestDto.getParams()))
                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        GetPagesResponseDto<Starship> actualResponse = starshipService.getStarshipsByPage(1, 1);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    void getStarshipById_ShouldReturnStarship_WhenStarshipExists() {
        Integer id = 1;
        ResultDto<Starship> expected = new ResultDto<>();
        expected.setProperties(new Starship());
        GetByIdResponseWrapperDto<Starship> expectedResponse = new GetByIdResponseWrapperDto<>("ok", expected);

        BuildRequestDto buildRequestDto = mockRequestGetById();
        when(buildRequestService.buildGetEntityByIdRequest(any())).thenReturn(buildRequestDto);
        when(restTemplate.exchange(
                eq("http://mocked-url/{id}"),
                eq(HttpMethod.GET),
                eq(buildRequestDto.getRequestEntity()),
                eq(new ParameterizedTypeReference<GetByIdResponseWrapperDto<Starship>>() {
                }),
                eq(id)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        GetByIdResponseWrapperDto<Starship> actualResponse = starshipService.getStarshipById(id);

        assertNotNull(actualResponse);
        assertEquals("ok", actualResponse.getMessage());
        assertEquals(expected, actualResponse.getResult());
    }

    @Test
    void getStarshipById_ShouldReturnNotFound_WhenStarshipDoesNotExist() {
        Integer id = 1;

        BuildRequestDto buildRequestDto = mockRequestGetById();
        when(buildRequestService.buildGetEntityByIdRequest(any())).thenReturn(buildRequestDto);
        when(restTemplate.exchange(
                eq("http://mocked-url/{id}"),
                eq(HttpMethod.GET),
                eq(buildRequestDto.getRequestEntity()),
                eq(new ParameterizedTypeReference<GetByIdResponseWrapperDto<Starship>>() {
                }),
                eq(id)
        )).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));


        GetByIdResponseWrapperDto<Starship> actualResponse = starshipService.getStarshipById(id);

        assertNotNull(actualResponse);
        assertEquals("La nave estelar no pudo ser encontrada", actualResponse.getMessage());
        assertNull(actualResponse.getResult());
    }

    @Test
    void getStarshipById_ThrowsException() {
        Integer id = 1;

        BuildRequestDto buildRequestDto = mockRequestGetById();
        when(buildRequestService.buildGetEntityByIdRequest(any())).thenReturn(buildRequestDto);
        when(restTemplate.exchange(
                eq("http://mocked-url/{id}"),
                eq(HttpMethod.GET),
                eq(buildRequestDto.getRequestEntity()),
                eq(new ParameterizedTypeReference<GetByIdResponseWrapperDto<Starship>>() {
                }),
                eq(id)
        )).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(Exception.class, () -> starshipService.getStarshipById(id));
    }

    @Test
    void getStarshispByName_ShouldReturnStarships_WhenNameMatches() {
        String name = "name";
        ResultDto<Starship> expected = new ResultDto<>();
        expected.setProperties(new Starship());
        GetByParamResponseWrapperDto<Starship> expectedResponse = new GetByParamResponseWrapperDto<>();
        expectedResponse.setMessage("ok");
        expectedResponse.setResult(Collections.singletonList(expected));

        BuildRequestDto buildRequestDto = mockRequestGetByTitle();
        when(buildRequestService.buildGetEntityByParamRequest("starships", "name", name))
                .thenReturn(buildRequestDto);
        when(restTemplate.exchange(
                eq("http://mocked-url"),
                eq(HttpMethod.GET),
                eq(buildRequestDto.getRequestEntity()),
                eq(new ParameterizedTypeReference<GetByParamResponseWrapperDto<Starship>>() {
                }),
                eq(Collections.singletonMap("name", name))
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        GetByParamResponseWrapperDto<Starship> actualResponse = starshipService.getStarshipsByName(name);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        assertEquals("ok", actualResponse.getMessage());
    }

    @Test
    void getStarshipsByName_ShouldReturnEmptyList_WhenNoStarshipsFound() {
        String name = "name";
        GetByParamResponseWrapperDto<Starship> expectedResponse = new GetByParamResponseWrapperDto<>();
        expectedResponse.setResult(Collections.emptyList());

        BuildRequestDto buildRequestDto = mockRequestGetByTitle();
        when(buildRequestService.buildGetEntityByParamRequest("starships", "name", name))
                .thenReturn(buildRequestDto);
        when(restTemplate.exchange(
                eq("http://mocked-url"),
                eq(HttpMethod.GET),
                eq(buildRequestDto.getRequestEntity()),
                eq(new ParameterizedTypeReference<GetByParamResponseWrapperDto<Starship>>() {
                }),
                eq(Collections.singletonMap("name", name))
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        GetByParamResponseWrapperDto<Starship> actualResponse = starshipService.getStarshipsByName(name);

        assertNotNull(actualResponse);
        assertTrue(actualResponse.getResult().isEmpty());
    }


    private BuildRequestDto mockRequestGetAllPaged() {
        BuildRequestDto requestDto = new BuildRequestDto();
        requestDto.setUrl("http://mocked-url");

        var requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("User-Agent", "ConexaChallenge/1.0");
        requestDto.setRequestEntity(new HttpEntity<>(requestHeaders));

        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("limit", "1");
        requestDto.setParams(params);

        return requestDto;
    }

    private BuildRequestDto mockRequestGetById() {
        BuildRequestDto requestDto = new BuildRequestDto();
        requestDto.setUrl("http://mocked-url");

        var requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("User-Agent", "ConexaChallenge/1.0");
        requestDto.setRequestEntity(new HttpEntity<>(requestHeaders));

        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        requestDto.setParams(params);

        return requestDto;
    }

    private BuildRequestDto mockRequestGetByTitle() {
        BuildRequestDto requestDto = new BuildRequestDto();
        requestDto.setUrl("http://mocked-url");

        var requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("User-Agent", "ConexaChallenge/1.0");
        requestDto.setRequestEntity(new HttpEntity<>(requestHeaders));

        Map<String, String> params = new HashMap<>();
        params.put("name", "name");
        requestDto.setParams(params);

        return requestDto;
    }
}
