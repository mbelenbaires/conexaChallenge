package com.example.conexa_challenge.controller;

import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetPagesResponseDto;
import com.example.conexa_challenge.entity.Starship;
import com.example.conexa_challenge.service.StarshipService;
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
@RequestMapping("/starships")
@Tag(name = "Naves estelares", description = "Información de naves estelares que forman parte de Star Wars en formato paginado, y filtradas por identificador o nombre.")
public class StarshipController {
    private final StarshipService starshipService;

    @GetMapping()
    @Operation(summary = "Lista de naves estelares", description = "Permite obtener de manera paginada las naves estelares que forman parte de Star Wars, con su información correspondiente. " +
            "Se puede ingresar el número de página que desea consultar, y configurar la cantidad de registros por página. En caso de no indicarlo, se devuelve por default la primer página, con 10 registros")
    public ResponseEntity<GetPagesResponseDto<Starship>> getStarshipByPage(
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        GetPagesResponseDto<Starship> response = starshipService.getStarshipsByPage(pageNumber, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar una nave estelar por id", description = "Permite buscar una nave estelar particular con su información correspondiente, mediante su id numérico.")
    public ResponseEntity<GetByIdResponseWrapperDto<Starship>> getStarshipById(
            @PathVariable Integer id) {
        GetByIdResponseWrapperDto<Starship> response = starshipService.getStarshipById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getByName")
    @Operation(summary = "Buscar naves estelares por nombre", description = "Permite obtener un listado de naves estelares cuyo nombre incluya el valor buscado.")
    public ResponseEntity<GetByParamResponseWrapperDto<Starship>> getStarshipByName(
            @RequestParam String name) {
        GetByParamResponseWrapperDto<Starship> response = starshipService.getStarshipsByName(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
