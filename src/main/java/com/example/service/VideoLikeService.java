package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.VideoLikeDTO;
import com.example.entity.ProfileEntity;
import com.example.entity.VideoLikeEntity;
import com.example.enums.HasLike;
import com.example.repository.VideoLikeRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VideoLikeService {
    @Autowired
    private VideoLikeRepository videoLikeRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;
    private VideoLikeDTO TO_DTO(VideoLikeEntity saved) {
        VideoLikeDTO dto = new VideoLikeDTO();
        dto.setVideoId(saved.getVideoId());
        dto.setHasLike(saved.getHasLike());
        return dto;
    }

    public ApiResponse like(String videoId) {
        ProfileEntity profile = SpringSecurityUtil.getProfileEntity();
        Optional<VideoLikeEntity> optionalVideoLike = videoLikeRepository
                .findByVideoIdAndPrtId(videoId, profile.getId());

        if (optionalVideoLike.isPresent()) {
            VideoLikeEntity entity = optionalVideoLike.get();

            if (entity.getPrtId().equals(profile.getId())) {

                if (entity.getHasLike() == null) {
                    entity.setHasLike(HasLike.LIKE); // if null updated to like

                } else if (entity.getHasLike().equals(HasLike.DISLIKE)){
                    entity.setHasLike(HasLike.LIKE); // if dislike updated to like
                } else {
                    entity.setHasLike(null); // if like updated to null
                }
                VideoLikeEntity updated = videoLikeRepository.save(entity);
                return new ApiResponse(true, TO_DTO(updated));

            } else {
                return new ApiResponse(false, resourceBundleService.getMessage("you.are.not.allowed", profile.getLanguage()));
            }

        } else {
            VideoLikeEntity entity = new VideoLikeEntity();
            entity.setVideoId(videoId);
            entity.setPrtId(profile.getId());
            entity.setHasLike(HasLike.LIKE);

            VideoLikeEntity saved = videoLikeRepository.save(entity);
            return new ApiResponse(true, TO_DTO(saved));
        }
    }

    public ApiResponse dislike(String videoId) {
        ProfileEntity profile = SpringSecurityUtil.getProfileEntity();
        Optional<VideoLikeEntity> optionalVideoLike = videoLikeRepository
                .findByVideoIdAndPrtId(videoId, profile.getId());

        if (optionalVideoLike.isPresent()) {
            VideoLikeEntity entity = optionalVideoLike.get();

            if (entity.getPrtId().equals(profile.getId())) {

                if (entity.getHasLike() == null) {
                    entity.setHasLike(HasLike.DISLIKE); // if null updated to like

                } else if (entity.getHasLike().equals(HasLike.LIKE)){
                    entity.setHasLike(HasLike.DISLIKE); // if dislike updated to like
                } else {
                    entity.setHasLike(null); // if like updated to null
                }
                VideoLikeEntity updated = videoLikeRepository.save(entity);
                return new ApiResponse(true, TO_DTO(updated));

            } else {
                return new ApiResponse(false, resourceBundleService.getMessage("you.are.not.allowed", profile.getLanguage()));
            }

        } else {
            VideoLikeEntity entity = new VideoLikeEntity();
            entity.setVideoId(videoId);
            entity.setPrtId(profile.getId());
            entity.setHasLike(HasLike.DISLIKE);

            VideoLikeEntity saved = videoLikeRepository.save(entity);
            return new ApiResponse(true, TO_DTO(saved));
        }
    }
}
