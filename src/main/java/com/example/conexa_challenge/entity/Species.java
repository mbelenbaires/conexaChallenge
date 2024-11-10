package com.example.conexa_challenge.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Species extends AuditoryAndUrl {
    private String name;
    private String classification;
    private String designation;
    private String average_height;
    private String average_lifespan;
    private String eye_colors;
    private String hair_colors;
    private String skin_colors;
    private String language;
    private String homeworld;
    List<Person> people;
    List<Film> films;
}
