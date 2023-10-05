package com.example.mapper.SubscriptionInfoMapper;

import com.example.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionInfoDTO {
    private String id;

    @NotBlank(message = "channel required")
    private ChannelInfoDTO channel;

    @NotBlank(message = "notificationType required")
    private NotificationType notificationType;
}
