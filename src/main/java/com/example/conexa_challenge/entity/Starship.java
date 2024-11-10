package com.example.conexa_challenge.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Starship extends Transport {

    @JsonProperty("starship_class")
    private String starshipClass;

    @JsonProperty("hyperdrive_rating")
    private String hyperdriveRating;

    @JsonProperty("MGLT")
    private String MGLT;
}
