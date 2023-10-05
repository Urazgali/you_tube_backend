package com.example.repository;

import com.example.entity.SubscriptionEntity;
import com.example.mapper.SubscriptionInfoMapper.SubscriptionInfoMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, String>{

    Optional<SubscriptionEntity> findByProfileIdAndChannelId(Integer profileId, String channelId);

    @Query("select s.id, (select s.channel.id, s.channel.name, (select s.channel.photo.id from SubscriptionEntity as s) from SubscriptionEntity as s), s.notificationType from SubscriptionEntity as s " +
            "where s.profileId =: profileId")
    List<SubscriptionInfoMapper> findByProfileId(@Param("profileId") Integer profileId);
}
