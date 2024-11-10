package com.example.conexa_challenge.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpEntity;

import java.util.Map;

@Getter
@Setter
public class BuildRequestDto {
    private String url;
    private HttpEntity<Object> requestEntity;
    private Map<String, String> params;
}
