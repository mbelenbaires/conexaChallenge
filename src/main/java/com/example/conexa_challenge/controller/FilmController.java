package com.example.conexa_challenge.controller;

import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetFilmsResponseWrapperDto;
import com.example.conexa_challenge.entity.Film;
import com.example.conexa_challenge.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/films")
@Tag(name = "Películas", description = "Información de películas que forman parte de Star Wars en formato paginado, y filtradas por parámetros.")
public class FilmController {
    private final FilmService filmService;

    @GetMapping()
    @Operation(summary = "Lista de películas", description = "Permite obtener el listado completo de películas de Star Wars, con su información correspondiente.")
    public ResponseEntity<GetFilmsResponseWrapperDto> getFilms() {
        GetFilmsResponseWrapperDto response = filmService.getFilms();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar una película por id", description = "Permite buscar una película particular con su información correspondiente, mediante su id numérico.")
    public ResponseEntity<GetByIdResponseWrapperDto<Film>> getPersonById(
            @PathVariable Integer id) {
        GetByIdResponseWrapperDto<Film> response = filmService.getFilmById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getByTitle")
    @Operation(summary = "Buscar películas por título", description = "Permite obtener un listado de películas de Star Wars, cuyo título incluya el valor buscado.")
    public ResponseEntity<GetByParamResponseWrapperDto<Film>> getPeopleByName(
            @RequestParam String title) {
        GetByParamResponseWrapperDto<Film> response = filmService.getFilmsByTitle(title);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
