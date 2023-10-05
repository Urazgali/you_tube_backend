package com.example.service;

import com.example.config.CustomUserDetails;
import com.example.dto.ApiResponse;
import com.example.dto.SubscriptionDTO;
import com.example.entity.SubscriptionEntity;
import com.example.enums.NotificationType;
import com.example.enums.SubscriptionStatus;
import com.example.mapper.SubscriptionInfoMapper.ChannelInfoDTO;
import com.example.mapper.SubscriptionInfoMapper.PhotoInfoDTO;
import com.example.mapper.SubscriptionInfoMapper.SubscriptionInfoDTO;
import com.example.mapper.SubscriptionInfoMapper.SubscriptionInfoMapper;
import com.example.repository.SubscriptionRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private AttachService attachService;

    public ApiResponse create(SubscriptionDTO dto) {
        CustomUserDetails customUserDetails = SpringSecurityUtil.getCurrentUser();

        SubscriptionEntity entity1 = getSubscription(customUserDetails.getProfile().getId(), dto.getChannelId());

        if (entity1 != null){
            return new ApiResponse(false, "YOU HAVE ALREADY SUBSCRIPT TO THIS CHANNEL");
        }

        SubscriptionEntity entity = new SubscriptionEntity();
        entity.setProfileId(customUserDetails.getProfile().getId());
        entity.setChannelId(dto.getChannelId());
        entity.setStatus(SubscriptionStatus.ACTIVE);
        entity.setNotificationType(dto.getNotificationType());
        subscriptionRepository.save(entity);

        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return new ApiResponse(true, dto);
    }
    public ApiResponse changeStatus(String channelId, SubscriptionStatus status) {
        CustomUserDetails customUserDetails = SpringSecurityUtil.getCurrentUser();

        SubscriptionEntity entity = getSubscription(customUserDetails.getProfile().getId(), channelId);
        if (entity == null){
            return new ApiResponse(false, "THIS SUBSCRIPTION IS NOT FOUND");
        }
        entity.setStatus(status);
        subscriptionRepository.save(entity);
        return new ApiResponse(true, "YOUR STATUS UPDATED SUCCESSFULLY");
    }


    public ApiResponse changeNotification(String channelId, NotificationType type) {
        CustomUserDetails customUserDetails = SpringSecurityUtil.getCurrentUser();

        SubscriptionEntity entity = getSubscription(customUserDetails.getProfile().getId(), channelId);
        if (entity == null){
            return new ApiResponse(false, "THIS SUBSCRIPTION IS NOT FOUND");
        }
        entity.setNotificationType(type);
        subscriptionRepository.save(entity);
        return new ApiResponse(true, "YOUR NOTIFICATION UPDATED SUCCESSFULLY");
    }


    public ApiResponse getAll() {
        CustomUserDetails customUserDetails = SpringSecurityUtil.getCurrentUser();

        List<SubscriptionInfoMapper> list = subscriptionRepository.findByProfileId(customUserDetails.getProfile().getId());
        List<SubscriptionInfoDTO> dtoList = new LinkedList<>();

        list.forEach(mapper -> {
            SubscriptionInfoDTO dto = new SubscriptionInfoDTO();
            dto.setId(mapper.getId());

            PhotoInfoDTO photo = new PhotoInfoDTO();
            photo.setId(mapper.getChannel().getPhoto().getId());
            photo.setUrl(attachService.getUrl(mapper.getChannel().getPhoto().getId()));

            ChannelInfoDTO channel = new ChannelInfoDTO();
            channel.setId(mapper.getChannel().getId());
            channel.setName(mapper.getChannel().getName());
            channel.setPhoto(photo);

            dto.setChannel(channel);
            dto.setNotificationType(mapper.getNotificationType());

            dtoList.add(dto);
        });

        return new ApiResponse(true, dtoList);
    }

    private SubscriptionEntity getSubscription(Integer profileId, String channelId) {
        Optional<SubscriptionEntity> optional = subscriptionRepository.findByProfileIdAndChannelId(profileId, channelId);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }
}
