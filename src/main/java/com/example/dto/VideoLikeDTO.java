package com.example.dto;

import com.example.dto.base.StringBaseDTO;
import com.example.enums.HasLike;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoLikeDTO extends StringBaseDTO {

    @NotNull(message = "VIDEO ID SHOULD NOT BE NULL!!")
    private String videoId;
    private VideoDTO video;

    @NotNull(message = "LIKE OR DISLIKE SHOULD NOT BE NULL!!")
    private HasLike hasLike;

    private Integer prtId;
}
