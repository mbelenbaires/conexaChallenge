package com.example.conexa_challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDto<T> {
    private T properties;
    private String description;

    @JsonProperty("_id")
    private String id;

    private String uid;

    @JsonProperty("__v")
    private String v;
}
