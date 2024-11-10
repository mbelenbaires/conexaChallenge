package com.example.conexa_challenge.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Transport extends AuditoryAndUrl {
    private String name;
    private String model;
    private String manufacturer;

    @JsonProperty("cost_in_credits")
    private String costInCredits;

    private String length;
    private String crew;
    private String passengers;

    @JsonProperty("max_atmosphering_speed")
    private String maxAtmospheringSpeed;

    @JsonProperty("cargo_capacity")
    private String cargoCapacity;

    private String consumables;
    private String uid;
    private List<String> films;
    private List<String> pilots;
}
