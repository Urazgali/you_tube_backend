package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.dto.TagDTO;
import com.example.entity.CategoryEntity;
import com.example.entity.TagEntity;
import com.example.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public TagDTO add(TagDTO dto) {
        TagEntity entity = toEntity(dto);
        tagRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public Boolean update(Integer id, TagDTO dto){
        int affectedRows = tagRepository.update(id, dto.getName());
        return affectedRows==1;
    }

    public String delete(Integer id) {
        tagRepository.deleteById(id);
        return "Category Deleted";
    }

    public List<TagDTO> getAll() {
        Iterable<TagEntity> iterable = tagRepository.findAll();
        List<TagDTO> dtoList = new LinkedList<>();
        iterable.forEach(entity ->{
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }

    private TagDTO toDTO(TagEntity entity) {
        TagDTO dto = new TagDTO();
        dto.setName(entity.getName());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    private TagEntity toEntity(TagDTO dto) {
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        return entity;
    }
}
