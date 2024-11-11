package com.example.conexa_challenge.controller;

import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetPagesResponseDto;
import com.example.conexa_challenge.entity.Person;
import com.example.conexa_challenge.service.PeopleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/people")
@Tag(name = "Personas", description = "Información de personas que forman parte de Star Wars en formato paginado,y filtradas por identificador o nombre.")
public class PeopleController {
    private final PeopleService peopleService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtiene personas en formato paginado.", description = "Permite obtener de manera paginada las personas que forman parte de Star Wars, con su información correspondiente." +
            "Se puede ingresar el número de página que desea consultar, y configurar la cantidad de registros por página. En caso de no indicarlo, se devuelve por default la primer página, con 10 registros.")
    public ResponseEntity<GetPagesResponseDto<Person>> getPeopleByPage(
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        GetPagesResponseDto<Person> response = peopleService.getPeopleByPage(pageNumber, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar una persona por id", description = "Permite buscar una persona particular con su información correspondiente, mediante su id numérico.")
    public ResponseEntity<GetByIdResponseWrapperDto<Person>> getPersonById(
            @PathVariable Integer id) {
        GetByIdResponseWrapperDto<Person> response = peopleService.getPersonById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getByName")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar personas por nombre", description = "Permite obtener un listado de personas que forman parte de Star Wars, cuyo nombre incluya el valor buscado.")
    public ResponseEntity<GetByParamResponseWrapperDto<Person>> getPeopleByName(
            @RequestParam String name) {
        GetByParamResponseWrapperDto<Person> response = peopleService.getPeopleByName(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
