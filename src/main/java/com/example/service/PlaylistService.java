package com.example.service;

import com.example.config.CustomUserDetails;
import com.example.dto.PlaylistDTO;
import com.example.entity.PlaylistEntity;
import com.example.mapper.PlayListInfoIMapper;
import com.example.repository.PlaylistRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

    public PlaylistDTO add(PlaylistDTO dto) {
        PlaylistEntity entity = toEntity(dto);
        CustomUserDetails customUserDetails = SpringSecurityUtil.getCurrentUser();
        entity.setPrtId(customUserDetails.getProfile().getId());
        playlistRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public Boolean update(Integer id, PlaylistDTO dto){
        int affectedRows = playlistRepository.update(id, dto.getName());
        return affectedRows==1;
    }

    public Boolean changeStatus(Integer id, PlaylistDTO dto) {
        Optional<PlaylistEntity> entity = playlistRepository.findById(id);
        if(entity.isPresent()){
            if (entity.get().getStatus().equals(dto.getStatus())){
                return false;
            }else {
                entity.get().setStatus(dto.getStatus());
        }
        }
        playlistRepository.save(entity.get());
        return true;

    }

    public String delete(Integer id) {
        playlistRepository.deleteById(id);
        return "Category Deleted";
    }

    public PageImpl<PlaylistDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC, "id");
        Page<PlaylistEntity> pageObj = playlistRepository.findAll(pageable);
        List<PlaylistDTO> playlistDTOList = pageObj.stream().map(this::toDto).collect(Collectors.toList());

        return new PageImpl<>(playlistDTOList, pageable, pageObj.getTotalElements());
    }


    private PlaylistDTO toDto(PlaylistEntity playlistEntity) {
        PlayListInfoIMapper mapper = new PlayListInfoIMapper() {
            @Override
            public Integer getId() {
                return playlistEntity.getId();
            }

            @Override
            public String getName() {
                return playlistEntity.getName();
            }

            @Override
            public String getDescription() {
                return playlistEntity.getDescription();
            }

            @Override
            public LocalDateTime createdDate() {
                return playlistEntity.getCreatedDate();
            }
        };

        // Now you can use the mapper to extract the required information
        Integer id = mapper.getId();
        String name = mapper.getName();
        String description = mapper.getDescription();
        LocalDateTime publishedDate = mapper.createdDate();

        // Create and return a PlaylistDTO object
        return new PlaylistDTO(id, name, description, publishedDate);
    }

    private PlaylistDTO toDTO(PlaylistEntity entity) {
        PlaylistDTO dto = new PlaylistDTO();

        dto.setName(entity.getName());
        dto.setVisible(entity.getVisible());
        dto.setDescription(entity.getDescription());
        dto.setChannelId(entity.getChannelId());
        dto.setStatus(entity.getStatus());
        dto.setOrderNum(entity.getOrderNum());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    private PlaylistEntity toEntity(PlaylistDTO dto) {
        PlaylistEntity entity = new PlaylistEntity();
        entity.setName(dto.getName());
        entity.setVisible(dto.getVisible());
        entity.setDescription(dto.getDescription());
        entity.setChannelId(dto.getChannelId());
        entity.setStatus(dto.getStatus());
        entity.setOrderNum(dto.getOrderNum());
        entity.setCreatedDate(dto.getCreatedDate());
        return entity;
    }
}
