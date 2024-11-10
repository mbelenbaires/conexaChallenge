package com.example.conexa_challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@JsonPropertyOrder({ "message", "totalRecords", "totalPages"})
public class GetPagesResponseDto<T> {
    private String message;

    @JsonProperty("total_records")
    private Integer totalRecords;

    @JsonProperty("total_pages")
    private Integer totalPages;

    private String previous;
    private String next;
    private List<T> results;
}
