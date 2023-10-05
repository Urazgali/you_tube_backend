package com.example.dto;

import com.example.enums.ChannelStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelDTO {

    private String id;

    @NotBlank(message = "Name required")
    private String name;

    @NotBlank(message = "Description required")
    private String description;

    @NotBlank(message = "Status required")
    private ChannelStatus status;

    @NotBlank(message = "Photo required")
    private String photoId;

    @NotBlank(message = "Photo required")
    private String bannerId;

    public ChannelDTO() {
    }
}
