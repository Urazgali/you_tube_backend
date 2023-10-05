package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilterResultDTO<T> {

    private List<T> content;
    private Long totalCount;

    public FilterResultDTO(List<T> content, Long totalCount) {
        this.content = content;
        this.totalCount = totalCount;
    }
}
