package com.example.dto;

import com.example.dto.base.StringBaseDTO;
import com.example.enums.VideoType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDTO extends StringBaseDTO {

    private Integer prtId;

    @NotNull(message = "CONTENT SHOULD NOT BE NULL!!!")
    @NotBlank(message = "CONTENT SHOULD NOT BE BLANK!!!")
    private String content;

    @NotNull(message = "CHANNEL ID SHOULD NOT BE NULL!!!")
    private String channelId;

    @NotNull(message = "VIDEO ID SHOULD NOT BE NULL!!!")
    private String videoId;

    @NotNull(message = "VIDEO TYPE SHOULD NOT BE NULL!!!")
    private VideoType videoType;
}
