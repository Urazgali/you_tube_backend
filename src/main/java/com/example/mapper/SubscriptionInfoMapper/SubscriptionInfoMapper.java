package com.example.mapper.SubscriptionInfoMapper;

import com.example.enums.NotificationType;

public interface SubscriptionInfoMapper {
    String getId();
    ChannelMapper getChannel();
    NotificationType getNotificationType();

}
