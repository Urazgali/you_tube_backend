package com.example.mapper.SubscriptionInfoMapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhotoInfoDTO {
    String id;

    @NotBlank(message = "Url required")
    private String url;
}
