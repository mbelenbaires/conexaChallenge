package com.example.conexa_challenge.controller;

import com.example.conexa_challenge.dto.UserDto;
import com.example.conexa_challenge.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/registration")
@Tag(name = "Registro", description = "Registro de nuevos usuarios")
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Permite registrar a un nuevo usuario determinando nombre y contraseña.")
    public ResponseEntity<String> registerNewUser(
            @RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<>(registrationService.registerNewUser(userDto), HttpStatus.CREATED);
    }

}
