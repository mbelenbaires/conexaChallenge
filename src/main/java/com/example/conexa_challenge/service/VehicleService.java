package com.example.conexa_challenge.service;

import com.example.conexa_challenge.dto.GetByIdResponseWrapperDto;
import com.example.conexa_challenge.dto.GetByParamResponseWrapperDto;
import com.example.conexa_challenge.dto.GetPagesResponseDto;
import com.example.conexa_challenge.entity.Vehicle;

public interface VehicleService {
    GetPagesResponseDto<Vehicle> getVehiclesByPage(Integer pageNumber, Integer pageSize);

    GetByIdResponseWrapperDto<Vehicle> getVehicleById(Integer id);

    GetByParamResponseWrapperDto<Vehicle> getVehiclesByName(String name);
}
