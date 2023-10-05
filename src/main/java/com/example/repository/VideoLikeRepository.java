package com.example.repository;

import com.example.entity.VideoLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoLikeRepository extends JpaRepository<VideoLikeEntity, String> {
    Optional<VideoLikeEntity> findByVideoIdAndPrtId(String videoId, Integer id);
}
