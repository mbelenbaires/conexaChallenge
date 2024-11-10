package com.example.conexa_challenge.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Vehicle extends Transport {

    @JsonProperty("vehicle_class")
    private String vehicleClass;
}
