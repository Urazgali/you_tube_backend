package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.dto.PlaylistDTO;
import com.example.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/playlist")
public class PlaylistController {
    /*1. Create Playlist (USER)
    2. Update Playlist(USER and OWNER)
    3. Change Playlist Status (USER and OWNER)
    4. Delete Playlist (USER and OWNER, ADMIN)
    5. Playlist Pagination (ADMIN)
        PlayListInfo*/

    @Autowired
    private PlaylistService playlistService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody PlaylistDTO dto){
        PlaylistDTO response = playlistService.add(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_OWNER','ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@RequestBody PlaylistDTO dto,
                                 @PathVariable("id") Integer id){
        playlistService.update(id, dto);
        return ResponseEntity.ok(true);
    }

    @PreAuthorize("hasAnyRole('ROLE_OWNER','ROLE_USER')")
    @PutMapping("status/{id}")
    public ResponseEntity<?> changeStatus(@RequestBody PlaylistDTO dto,
                                 @PathVariable("id") Integer id){
        Boolean response = playlistService.changeStatus(id, dto);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_OWNER','ROLE_USER','ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        String  response = playlistService.delete(id);
        if(response.length()>0){
            return ResponseEntity.ok("Playlist Deleted");
        }
        return ResponseEntity.badRequest().body("Playlist not found");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/pagination")
    public ResponseEntity<?> pagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size) {

        return ResponseEntity.ok(playlistService.pagination(page - 1, size));
    }
}
