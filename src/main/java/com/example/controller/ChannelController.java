package com.example.controller;


import com.example.dto.ApiResponse;
import com.example.dto.ChannelDTO;
import com.example.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/channel")
public class ChannelController {
    @Autowired
    private ChannelService channelService;
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@RequestBody ChannelDTO dto){
            return ResponseEntity.status(HttpStatus.CREATED).body(channelService.add(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_OWNER','ROLE_USER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody ChannelDTO dto,
                                    @PathVariable("id") String id){
        Boolean response =channelService.update(id,dto);
        return ResponseEntity.ok( response);
    }

    @PreAuthorize("hasAnyRole('ROLE_OWNER','ROLE_USER')")
    @PutMapping("/update/photo/{id}")
    public ResponseEntity<?> photoUp(@RequestBody ChannelDTO dto, @PathVariable("id") String id){
        Boolean response =channelService.PhotoUP(id,dto);
        return ResponseEntity.ok( response);
    }

    @PreAuthorize("hasAnyRole('ROLE_OWNER','ROLE_USER')")
    @PutMapping("/update/banner/{id}")
    public ResponseEntity<?> bannerUp(@RequestBody ChannelDTO dto, @PathVariable("id") String id){
        Boolean response =channelService.bannerId(id,dto);
        return ResponseEntity.ok( response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @GetMapping(value = "/pagination")
//    public ResponseEntity<?> pagination(@RequestParam(value = "page", defaultValue = "1") int page,
//                                        @RequestParam(value = "size", defaultValue = "5") int size) {
//
//        return ResponseEntity.ok(channelService.pagination(page - 1, size));
//    }
    @GetMapping(value = "/pagination")
    public ResponseEntity<?> pagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        PageImpl<ChannelDTO> response = channelService.pagination(page - 1, size);
        return ResponseEntity.ok(response);

    }
    @GetMapping("/search/{id}")
    public ResponseEntity<ChannelDTO> search(@PathVariable("id") String id){
        return ResponseEntity.ok(channelService.search(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_OWNER','ROLE_USER','ROLE_ADMIN')")
    @PutMapping("/update/status/{id}")
    public ResponseEntity<?> statusUp(@RequestBody ChannelDTO dto,
                                      @PathVariable("id") String id){
        return ResponseEntity.ok(channelService.statusUpdate(id,dto));
    }

    @GetMapping("/all")
    public ResponseEntity<?> all(){
        return ResponseEntity.ok(channelService.all());
    }


}
