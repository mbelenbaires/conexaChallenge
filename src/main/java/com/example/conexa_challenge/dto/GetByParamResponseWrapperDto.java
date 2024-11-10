package com.example.conexa_challenge.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetByParamResponseWrapperDto<T> {
    private String message;
    private List<ResultDto<T>> result;
}
