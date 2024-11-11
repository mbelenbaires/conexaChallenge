package com.example.conexa_challenge.controller;

import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetPagesResponseDto;
import com.example.conexa_challenge.entity.Vehicle;
import com.example.conexa_challenge.service.VehicleService;
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
@RequestMapping("/vehicles")
@Tag(name = "Vehículos", description = "Información de vehículos que forman parte de Star Wars en formato paginado, y filtrados por identificador o nombre.")
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtiene vehículos en formato paginado", description = "Permite obtener de manera paginada los vehículos que forman parte de Star Wars, con su información correspondiente. " +
            "Se puede ingresar el número de página que desea consultar, y configurar la cantidad de registros por página. En caso de no indicarlo, se devuelve por default la primer página, con 10 registros.")
    public ResponseEntity<GetPagesResponseDto<Vehicle>> getVehiclesByPage(
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        GetPagesResponseDto<Vehicle> response = vehicleService.getVehiclesByPage(pageNumber, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar un vehículo por id", description = "Permite buscar un vehículo particular con su información correspondiente, mediante su id numérico.")
    public ResponseEntity<GetByIdResponseWrapperDto<Vehicle>> getVehicleById(
            @PathVariable Integer id) {
        GetByIdResponseWrapperDto<Vehicle> response = vehicleService.getVehicleById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getByName")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar vehículos por nombre", description = "Permite obtener un listado de vehículos cuyo nombre incluya el valor buscado.")
    public ResponseEntity<GetByParamResponseWrapperDto<Vehicle>> getVehiclesByName(
            @RequestParam String name) {
        GetByParamResponseWrapperDto<Vehicle> response = vehicleService.getVehiclesByName(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
