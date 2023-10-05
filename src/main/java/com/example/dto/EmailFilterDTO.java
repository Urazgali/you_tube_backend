package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmailFilterDTO {
    private String email;
    private LocalDate createdDate;
}
