package com.example.dto;import com.fasterxml.jackson.annotation.JsonInclude;import lombok.Getter;import lombok.Setter;import javax.validation.constraints.NotBlank;import javax.validation.constraints.NotNull;import javax.validation.constraints.Size;@Getter@Setter@JsonInclude(JsonInclude.Include.NON_NULL)public class AuthDTO {    @NotNull(message = "email is null!")    @NotBlank(message = "email is blank!")    private String email;    @NotNull(message = "email is null!")    @NotBlank(message = "email is blank!")    @Size(min = 8, message = "password should be minimum 8!")    private String password;}