package com.example.entity;

import com.example.entity.Base.StringBaseEntity;
import com.example.enums.VideoType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "report")
public class ReportEntity extends StringBaseEntity {
    @Column(name = "prt_id")
    private Integer prtId;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "channel_id")
    private String channelId;
    @ManyToOne
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private ChannelEntity channelEntity;

    @Column(name = "video_id")
    private String videoId;
    @ManyToOne
    @JoinColumn(name = "video_id",  updatable = false, insertable = false)
    private VideoEntity videoEntity;

    @Column(name = "video_type")
    @Enumerated(EnumType.STRING)
    private VideoType videoType;
}
