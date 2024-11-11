package com.example.conexa_challenge.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class GetByParamResponseWrapperDto<T> {
    private String message;
    private List<ResultDto<T>> result;
}
