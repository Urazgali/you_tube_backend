package com.example.dto;

import com.example.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtDTO {
    private Integer id;
    private String email;

    public JwtDTO(Integer id) {
        this.id = id;
    }

    public JwtDTO(String email) {
        this.email = email;
    }

}
