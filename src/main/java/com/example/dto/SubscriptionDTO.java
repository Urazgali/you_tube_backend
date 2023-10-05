package com.example.dto;

import com.example.enums.NotificationType;
import com.example.enums.SubscriptionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionDTO {
    private String id;

    @NotBlank(message = "ProfileId required")
    @NotNull(message = "ProfileId is null")
    private Integer profileId;
    @NotBlank(message = "channelId required")
    @NotNull(message = "channelId is null")
    private String channelId;
    private LocalDateTime createdDate;
    private LocalDateTime unsubscribeDate;

    @NotBlank(message = "status required")
    private SubscriptionStatus status;
    @NotBlank(message = "notificationType required")
    private NotificationType notificationType;
}
