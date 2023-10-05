package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegDTO {
    @NotBlank(message = "Name field cannot be empty")
    private String name;
    @NotBlank(message = "Surname field cannot be empty")
    private String surname;
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Password field cannot be empty")
    @Size(min = 3,message = "Password must be at least 3 characters long")
    private String password;
    private String photo;
}
