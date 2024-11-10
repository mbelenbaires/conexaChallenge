package com.example.conexa_challenge.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person extends AuditoryAndUrl {
    private String uid;
    private String name;

    @JsonProperty("birth_year")
    private String birthYear;

    @JsonProperty("eye_color")
    private String eyeColor;

    private String gender;

    @JsonProperty("hair_color")
    private String hairColor;

    private String height;
    private String mass;

    @JsonProperty("skin_color")
    private String skinColor;

    private String homeworld;
    private List<String> films;
    private List<String> species;
    private List<String> starships;
    private List<String> vehicles;
}
