package com.example.dto.base;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class IntegerBaseDTO extends BaseDTO{
    private Integer id;

    public IntegerBaseDTO(Integer id, LocalDateTime createdDate) {
        super(createdDate);
        this.id = id;
    }

    public  IntegerBaseDTO(){

    }

}
