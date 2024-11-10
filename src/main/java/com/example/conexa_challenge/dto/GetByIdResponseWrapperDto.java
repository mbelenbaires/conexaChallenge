package com.example.conexa_challenge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetByIdResponseWrapperDto<T> {
    private String message;
    private ResultDto<T> result;
}
