package com.example.conexa_challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class GetFilmsResponseWrapperDto {
    private String message;
    private List<PropertiesDto> result;

    @JsonProperty("total_records")
    private Integer totalRecords;

    @JsonProperty("total_pages")
    private Integer totalPages;

    @JsonProperty("page_number")
    private Integer pageNumber;

    @JsonProperty("page_size")
    private Integer pageSize;
}
