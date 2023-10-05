package com.example.controller;

import com.example.dto.TagDTO;
import com.example.entity.TagEntity;
import com.example.service.TagService;
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
@RequestMapping("/api/v1/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody TagDTO dto){
        TagDTO response = tagService.add(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @CachePut(cacheNames = "tag", key = "#id")
    public ResponseEntity<?> put(@RequestBody TagDTO dto,
                                 @PathVariable("id") Integer id){
        tagService.update(id, dto);
        return ResponseEntity.ok(true);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @CacheEvict(cacheNames = "tag", key = "#id")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        String  response = tagService.delete(id);
        if(!response.isEmpty()){
            return ResponseEntity.ok("Tag Deleted");
        }
        return ResponseEntity.badRequest().body("Tag not found");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public List<TagDTO> all(){
        return tagService.getAll();
    }

    @Scheduled(cron = "0 30 0 0 0 0")
    @CacheEvict(value = "tag", allEntries = true)
    void cleanCaches() {
        log.info("cache tag cleared");
    }
}
