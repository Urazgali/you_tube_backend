package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CategoryDTO category){
        CategoryDTO response = categoryService.add(category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @CachePut(cacheNames = "category", key = "#id")
    public ResponseEntity<?> put(@RequestBody CategoryDTO category,
                                 @PathVariable("id") Integer id){
        categoryService.update(id, category);
        return ResponseEntity.ok(true);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @CacheEvict(cacheNames = "category", key = "#id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        String  response = categoryService.delete(id);
        if(!response.isEmpty()){
            return ResponseEntity.ok("Category Deleted");
        }
        return ResponseEntity.badRequest().body("Category not found");
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public List<CategoryDTO> all(){
        return categoryService.getAll();
    }

    @Scheduled(cron = "0 30 0 0 0 0")
    @CacheEvict(value = "category", allEntries = true)
    void cleanCaches() {
        log.info("cache category cleared");
    }
}

