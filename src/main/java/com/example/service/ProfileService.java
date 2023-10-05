package com.example.service;import com.example.config.CustomUserDetails;import com.example.dto.ApiResponse;import com.example.dto.AttachDTO;import com.example.dto.ProfileDTO;import com.example.entity.ProfileEntity;import com.example.enums.ProfileRole;import com.example.enums.ProfileStatus;import com.example.repository.ProfileRepository;import com.example.util.MD5Util;import com.example.util.SpringSecurityUtil;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import java.util.Optional;@Servicepublic class ProfileService {    @Autowired    private ProfileRepository profileRepository;    @Autowired    private EmailSenderService emailSenderService;    public ApiResponse create(ProfileDTO dto) {        CustomUserDetails customUserDetails = SpringSecurityUtil.getCurrentUser();        Optional<ProfileEntity> profileByEmile = profileRepository.findByEmail(dto.getEmail());        if (profileByEmile.isPresent()) {//            throw new AppBadRequestException("Email already exists");            return new ApiResponse(false, "Email already exists");        }        ProfileEntity entity = new ProfileEntity();        entity.setName(dto.getName());        entity.setSurname(dto.getSurname());        entity.setEmail(dto.getEmail());        entity.setPassword(MD5Util.encode(dto.getPassword()));        entity.setStatus(ProfileStatus.ACTIVE);        entity.setRole(ProfileRole.ROLE_ADMIN);        entity.setPrtId(customUserDetails.getProfile().getId());        profileRepository.save(entity);        dto.setId(entity.getId());        dto.setStatus(entity.getStatus());        dto.setRole(entity.getRole());        dto.setCreatedDate(entity.getCreatedDate());        return new ApiResponse(true, dto);    }    public ApiResponse updateDetail(Integer profileId,ProfileDTO dto) {        int effectedRows = profileRepository.updateDetail(profileId, dto.getName(), dto.getSurname());        if (effectedRows == 0){            return new ApiResponse(false, "Detail Not Updated");        }        return new ApiResponse(true, "Detail Updated");    }    public ApiResponse getDetail() {//        ProfileEntity entity = profileRepository.getDetail(profileId);//        if (entity == null){//            return new ApiResponse(false, "Profile Id Not Found");//        }        CustomUserDetails customUserDetails = SpringSecurityUtil.getCurrentUser();        ProfileEntity entity = customUserDetails.getProfile();        ProfileDTO dto = new ProfileDTO();        dto.setId(entity.getId());        dto.setName(entity.getName());        dto.setSurname(entity.getSurname());        dto.setEmail(entity.getEmail());        dto.setPhotoUrl(entity.getPhotoUrl());        return new ApiResponse(true, dto);    }    public ApiResponse updateAttach(Integer profileId, AttachDTO attachDTO) {        int effectedRows = profileRepository.updateAttach(profileId, attachDTO.getId(), attachDTO.getUrl());        if (effectedRows == 0){            return new ApiResponse(false, "Image Not Updated");        }        return new ApiResponse(true, "Image Updated");    }    public ApiResponse updateEmail(String email) {        CustomUserDetails customUserDetails = SpringSecurityUtil.getCurrentUser();        ProfileEntity profile = customUserDetails.getProfile();        ApiResponse response = emailSenderService                .sendUpdateEmailVerification(profile.getEmail(), email);        return response;    }    public ApiResponse changePassword(String password){        CustomUserDetails customUserDetails = SpringSecurityUtil.getCurrentUser();        ProfileEntity profile = customUserDetails.getProfile();        ApiResponse response = emailSenderService                .sendUpdatePasswordVerification(profile.getEmail(), password);        return response;    }}