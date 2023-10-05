package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.ReportDTO;
import com.example.entity.ProfileEntity;
import com.example.entity.ReportEntity;
import com.example.enums.ProfileRole;
import com.example.repository.ReportRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;

    private ReportEntity TO_ENTITY(ReportDTO dto) {
        ReportEntity entity = new ReportEntity();
        entity.setChannelId(dto.getChannelId());
        entity.setVideoId(dto.getVideoId());
        entity.setContent(dto.getContent());
        entity.setVideoType(dto.getVideoType());
        return entity;
    }

    private ReportDTO TO_DTO(ReportEntity entity) {
        ReportDTO dto = new ReportDTO();
        dto.setChannelId(entity.getChannelId());
        dto.setVideoId(entity.getVideoId());
        dto.setContent(entity.getContent());
        dto.setVideoType(entity.getVideoType());
        return dto;
    }

    public ApiResponse create(ReportDTO dto) {
        Integer prtId = SpringSecurityUtil.getCurrentProfileId();

        ReportEntity entity = TO_ENTITY(dto);
        entity.setPrtId(prtId);
        ReportEntity saved = reportRepository.save(entity);
        return new ApiResponse(true, TO_DTO(saved));
    }

    public Page<ReportDTO> paging(Integer page, Integer size) {
        Sort sort = Sort.by("createdDate")
                .ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ReportEntity> reportEntityPage = reportRepository
                .findAll(pageable);
        List<ReportDTO> reportDTOList = reportEntityPage
                .stream()
                .map(this::TO_DTO)
                .toList();
        return new PageImpl<>(reportDTOList, pageable, reportEntityPage.getTotalElements());
    }

    public ApiResponse remove(String id) {
        ProfileEntity profile = SpringSecurityUtil.getProfileEntity();
        Optional<ReportEntity> optionalReport = reportRepository.findById(id);
        if (optionalReport.isPresent()) {
            ReportEntity entity = optionalReport.get();
            if (entity.getPrtId().equals(profile.getId()) ||
            profile.getRole().equals(ProfileRole.ROLE_ADMIN)) {
                reportRepository.deleteById(id);
                return new ApiResponse(true, resourceBundleService.getMessage("success.deleted", profile.getLanguage()));
            } else {
                return new ApiResponse(true, resourceBundleService.getMessage("you.are.not.allowed", profile.getLanguage()));
            }
        } else {
            return new ApiResponse(true, resourceBundleService.getMessage("item.not.found", profile.getLanguage()));
        }
    }

    public List<ReportDTO> getListById(Integer id) {
        List<ReportEntity> reportDTOList =reportRepository.findAllByPrtId(id);
        return reportDTOList
                .stream()
                .map(this::TO_DTO)
                .toList();
    }
}
