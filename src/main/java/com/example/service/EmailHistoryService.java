package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.dto.EmailHistoryDTO;
import com.example.dto.EmailHistoryFilterDTO;
import com.example.dto.FilterResultDTO;
import com.example.entity.CategoryEntity;
import com.example.entity.EmailHistoryEntity;
import com.example.exp.ItemNotFoundException;
import com.example.repository.CustomEmailHistoryRepository;
import com.example.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailHistoryService {

    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    @Autowired
    private CustomEmailHistoryRepository customEmailHistoryRepository;

    public PageImpl<EmailHistoryDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC, "id");
        Page<EmailHistoryEntity> pageObj = emailHistoryRepository.findAll(pageable);
        List<EmailHistoryDTO> historyDTOList = pageObj.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(historyDTOList, pageable, pageObj.getTotalElements());
    }

    public PageImpl<EmailHistoryDTO> paginationByEmail(int page, int size, String  email){
        Pageable pageable = PageRequest.of(page,size);
        Page<EmailHistoryEntity> pageObj = emailHistoryRepository.findAllByToEmailOrderByCreatedDate(email,pageable);
        List<EmailHistoryDTO> historyDTOList = pageObj.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(historyDTOList, pageable, pageObj.getTotalElements());
    }

    public PageImpl<EmailHistoryDTO> filter(EmailHistoryFilterDTO filterDTO, int page, int size) {
        FilterResultDTO<EmailHistoryEntity> result = customEmailHistoryRepository.filter(filterDTO, page, size);
        List<EmailHistoryDTO> historyDTOList = result.getContent().stream()
                .map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(historyDTOList, PageRequest.of(page, size), result.getTotalCount());
    }

    private EmailHistoryDTO toDTO(EmailHistoryEntity entity) {
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setToEmail(entity.getToEmail());
        dto.setTitle(entity.getTitle());
        dto.setMessage(entity.getMessage());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private List<EmailHistoryDTO> getCourseDTOS(List<EmailHistoryEntity> entityList) {
        List<EmailHistoryDTO> dtoList = new LinkedList<>();
        if (entityList.isEmpty()) {
            throw new ItemNotFoundException("Email History not found");
        }
        for (EmailHistoryEntity s:entityList){
            dtoList.add(toDTO(s));
        }
        return dtoList;
    }


}
