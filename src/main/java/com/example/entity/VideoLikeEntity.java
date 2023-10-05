package com.example.entity;

import com.example.entity.Base.StringBaseEntity;
import com.example.enums.HasLike;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "video_like")
public class VideoLikeEntity extends StringBaseEntity {

    @Column(name = "video_id")
    private String videoId;
    @OneToOne
    @JoinColumn(name = "video_id", updatable = false, insertable = false)
    private VideoEntity videoEntity;

    @Column(name = "has_like")
    @Enumerated(EnumType.STRING)
    private HasLike hasLike;

    @Column(name = "prt_id")
    private Integer prtId;
}
