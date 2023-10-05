package com.example.entity;

import com.example.entity.Base.IntegerBaseEntity;
import com.example.enums.PlaylistStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "playlist")
@Getter
@Setter
public class PlaylistEntity extends IntegerBaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PlaylistStatus status;

    @Column(name = "order_num")
    private Integer orderNum;

    @Column(name = "prt_id")
    private Integer prtId;

    @Column(name = "channel_id")
    private String channelId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private ChannelEntity channelEntity;

}
