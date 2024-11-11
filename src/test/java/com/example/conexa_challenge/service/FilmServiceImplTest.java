package com.example.conexa_challenge.service;

import com.example.conexa_challenge.dto.BuildRequestDto;
import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetFilmsResponseWrapperDto;
import com.example.conexa_challenge.dto.PropertiesDto;
import com.example.conexa_challenge.dto.ResultDto;
import com.example.conexa_challenge.entity.Film;
import com.example.conexa_challenge.service.impl.FilmServiceImpl;
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
public class FilmServiceImplTest {
    @InjectMocks
    private FilmServiceImpl filmService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private BuildRequestService buildRequestService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(filmService, "swapiBaseUrl", "http://mocked-url");
    }

    @Test
    void getFilms_ShouldReturnFilms_WhenSuccessful() {
        GetFilmsResponseWrapperDto expectedResponse = new GetFilmsResponseWrapperDto();
        expectedResponse.setMessage("ok");
        PropertiesDto propertiesDto = new PropertiesDto();
        propertiesDto.setProperties(new Film());
        expectedResponse.setResult(Collections.singletonList(propertiesDto));

        BuildRequestDto requestDto = mockRequestGetAll();

        when(restTemplate.exchange("http://mocked-urlfilms", HttpMethod.GET, requestDto.getRequestEntity(),
                GetFilmsResponseWrapperDto.class))
                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        GetFilmsResponseWrapperDto actualResponse = filmService.getFilms();

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    void getFilmById_ShouldReturnFilm_WhenFilmExists() {
        Integer filmId = 1;
        ResultDto<Film> expectedFilm = new ResultDto<>();
        expectedFilm.setProperties(new Film());
        GetByIdResponseWrapperDto<Film> expectedResponse = new GetByIdResponseWrapperDto<>("ok", expectedFilm);

        BuildRequestDto buildRequestDto = mockRequestGetById();
        when(buildRequestService.buildGetEntityByIdRequest(any())).thenReturn(buildRequestDto);
        when(restTemplate.exchange(
                eq("http://mocked-url/{id}"),
                eq(HttpMethod.GET),
                eq(buildRequestDto.getRequestEntity()),
                eq(new ParameterizedTypeReference<GetByIdResponseWrapperDto<Film>>() {
                }),
                eq(filmId)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        GetByIdResponseWrapperDto<Film> actualResponse = filmService.getFilmById(filmId);

        assertNotNull(actualResponse);
        assertEquals("ok", actualResponse.getMessage());
        assertEquals(expectedFilm, actualResponse.getResult());
    }

    @Test
    void getFilmById_ShouldReturnNotFound_WhenFilmDoesNotExist() {
        Integer filmId = 1;

        BuildRequestDto buildRequestDto = mockRequestGetById();
        when(buildRequestService.buildGetEntityByIdRequest(any())).thenReturn(buildRequestDto);
        when(restTemplate.exchange(
                eq("http://mocked-url/{id}"),
                eq(HttpMethod.GET),
                eq(buildRequestDto.getRequestEntity()),
                eq(new ParameterizedTypeReference<GetByIdResponseWrapperDto<Film>>() {
                }),
                eq(filmId)
        )).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));


        GetByIdResponseWrapperDto<Film> actualResponse = filmService.getFilmById(filmId);

        assertNotNull(actualResponse);
        assertEquals("La película no pudo ser encontrada", actualResponse.getMessage());
        assertNull(actualResponse.getResult());
    }

    @Test
    void getFilmById_ThrowsException() {
        Integer filmId = 1;

        BuildRequestDto buildRequestDto = mockRequestGetById();
        when(buildRequestService.buildGetEntityByIdRequest(any())).thenReturn(buildRequestDto);
        when(restTemplate.exchange(
                eq("http://mocked-url/{id}"),
                eq(HttpMethod.GET),
                eq(buildRequestDto.getRequestEntity()),
                eq(new ParameterizedTypeReference<GetByIdResponseWrapperDto<Film>>() {
                }),
                eq(filmId)
        )).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(Exception.class, () -> filmService.getFilmById(filmId));
    }

    @Test
    void getFilmsByTitle_ShouldReturnFilms_WhenTitleMatches() {
        String title = "title";
        ResultDto<Film> expectedFilm = new ResultDto<>();
        expectedFilm.setProperties(new Film());
        GetByParamResponseWrapperDto<Film> expectedResponse = new GetByParamResponseWrapperDto<>();
        expectedResponse.setMessage("ok");
        expectedResponse.setResult(Collections.singletonList(expectedFilm));

        BuildRequestDto buildRequestDto = mockRequestGetByTitle();
        when(buildRequestService.buildGetEntityByParamRequest("films", "title", title))
                .thenReturn(buildRequestDto);
        when(restTemplate.exchange(
                eq("http://mocked-url"),
                eq(HttpMethod.GET),
                eq(buildRequestDto.getRequestEntity()),
                eq(new ParameterizedTypeReference<GetByParamResponseWrapperDto<Film>>() {
                }),
                eq(Collections.singletonMap("title", title))
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        GetByParamResponseWrapperDto<Film> actualResponse = filmService.getFilmsByTitle(title);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        assertEquals("ok", actualResponse.getMessage());
    }

    @Test
    void getFilmsByTitle_ShouldReturnEmptyList_WhenNoFilmsFound() {
        String title = "title";
        GetByParamResponseWrapperDto<Film> expectedResponse = new GetByParamResponseWrapperDto<>();
        expectedResponse.setResult(Collections.emptyList());

        BuildRequestDto buildRequestDto = mockRequestGetByTitle();
        when(buildRequestService.buildGetEntityByParamRequest("films", "title", title))
                .thenReturn(buildRequestDto);
        when(restTemplate.exchange(
                eq("http://mocked-url"),
                eq(HttpMethod.GET),
                eq(buildRequestDto.getRequestEntity()),
                eq(new ParameterizedTypeReference<GetByParamResponseWrapperDto<Film>>() {
                }),
                eq(Collections.singletonMap("title", title))
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        GetByParamResponseWrapperDto<Film> actualResponse = filmService.getFilmsByTitle(title);

        assertNotNull(actualResponse);
        assertTrue(actualResponse.getResult().isEmpty());
    }

    private BuildRequestDto mockRequestGetAll() {
        BuildRequestDto requestDto = new BuildRequestDto();
        requestDto.setUrl("http://mocked-url");

        var requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("User-Agent", "ConexaChallenge/1.0");
        requestDto.setRequestEntity(new HttpEntity<>(requestHeaders));

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
        params.put("title", "title");
        requestDto.setParams(params);

        return requestDto;
    }
}
