package com.example.conexa_challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetByIdResponseWrapperDto<T> {
    private String message;
    private ResultDto<T> result;
}
