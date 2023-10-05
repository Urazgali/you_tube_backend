package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailHistoryFilterDTO {
    private Integer id;
    private String toEmail;
    private String title;
    private String message;
    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;
}
