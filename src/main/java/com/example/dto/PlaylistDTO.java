package com.example.dto;

import com.example.dto.base.IntegerBaseDTO;
import com.example.dto.base.StringBaseDTO;
import com.example.entity.ChannelEntity;
import com.example.enums.PlaylistStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistDTO extends IntegerBaseDTO {

    private String name;
    private String description;
    private PlaylistStatus status;
    private Integer orderNum;
    private String channelId;
    private Integer prtId;
//    id, name, description, publishedDate

    public PlaylistDTO( Integer id, String name, String description, LocalDateTime createdDate) {
        super(id, createdDate);
        this.name = name;
        this.description = description;
    }

    public PlaylistDTO() {
        super();
    }
}
