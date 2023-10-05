package com.example.entity;

import com.example.entity.Base.IntegerBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tag")
@Getter
@Setter
public class TagEntity extends IntegerBaseEntity {

    @Column(name = "name")
    private String name;
}
