package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.entity.CategoryEntity;
import com.example.enums.Language;
import com.example.exception.ItemNotFoundException;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO add(CategoryDTO dto) {
        CategoryEntity entity = toEntity(dto);
        categoryRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    @CachePut(cacheNames = "category", key = "#id")
    public Boolean update(Integer id, CategoryDTO dto){
        int affectedRows = categoryRepository.update(id, dto.getName());
        return affectedRows==1;
    }

    @CacheEvict(value = "category", key = "#id")
    public String delete(Integer id) {
        categoryRepository.deleteById(id);
        return "Category Deleted";
    }
    @Scheduled(cron = "0,30 * * * * ?")
    @CacheEvict(value = "category", allEntries = true)
    public void deleteAll() {}

    public List<CategoryDTO> getAll() {
        Iterable<CategoryEntity> iterable = categoryRepository.findAll();
        List<CategoryDTO> dtoList = new LinkedList<>();
        iterable.forEach(entity ->{
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }

    @Cacheable(value = "category", key = "#id")
    public CategoryDTO getById(Integer id, Language lang) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.map(this::toDTO).orElse(null);
    }

    private CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setName(entity.getName());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    private CategoryEntity toEntity(CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        return entity;
    }
}
