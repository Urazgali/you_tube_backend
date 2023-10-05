package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProfileUpdateDetailDTO {
    @NotBlank(message = "Name field cannot be empty")
    private String name;
    @NotBlank(message = "Surname field cannot be empty")
    private String surname;
}
