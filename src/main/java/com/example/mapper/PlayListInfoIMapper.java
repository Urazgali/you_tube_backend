package com.example.mapper;

import java.time.LocalDateTime;

public interface PlayListInfoIMapper {
    Integer getId();

    String getName();

    String getDescription();

    LocalDateTime createdDate();
}
