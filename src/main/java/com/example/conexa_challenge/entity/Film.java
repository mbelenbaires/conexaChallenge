package com.example.conexa_challenge.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class Film extends AuditoryAndUrl {
    private String title;

    @JsonProperty("episode_id")
    private Integer episodeId;

    @JsonProperty("opening_crawl")
    private String openingCrawl;

    private String director;
    private String producer;

    @JsonProperty("release_date")
    private LocalDate releaseDate;

    List<String> species;
    private List<String> starships;
    private List<String> vehicles;
    private List<String> characters;
    private List<String> planets;
}
