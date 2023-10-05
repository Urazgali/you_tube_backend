package com.example.mapper.SubscriptionInfoMapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelInfoDTO {
    private String id;

    @NotBlank(message = "Name required")
    private String name;

    @NotBlank(message = "Notification required")
    private PhotoInfoDTO photo;
}
