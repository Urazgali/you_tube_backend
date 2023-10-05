package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.ReportDTO;
import com.example.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
@Tag(name = "REPORT CONTROLLER #️⃣", description = "this api used for report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @Operation(summary = "create report ➕", description = "this api used for report creation")
    @PostMapping("/close/ceate")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody ReportDTO dto) {
        ApiResponse response = reportService.create(dto);
        if (response.getStatus()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(response);
        }
    }
    @Operation(summary = "paging reports 📄#️⃣", description = "this api used for paging reports")
    @GetMapping("/close/paging")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ReportDTO>> paging(@RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "30") Integer size) {
        Page<ReportDTO> reportDTOPage = reportService.paging(page, size);
        return ResponseEntity.ok(reportDTOPage);
    }

    @Operation(summary = "get list reports 📄#️⃣", description = "this api used for get list reports")
    @GetMapping("/close/list-by-profile/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReportDTO>> getListById(@PathVariable Integer id) {
        return ResponseEntity.ok(reportService.getListById(id));
    }
    @Operation(summary = "remove report ❌", description = "this api used for remove report")
    @DeleteMapping("/close/delete/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse> remove(@PathVariable String id) {
        ApiResponse response = reportService.remove(id);
        if (response.getStatus()) {
            return ResponseEntity
//                    .status(HttpStatus.NO_CONTENT)
//                    .body( response);
                    .ok(response);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(response);
        }
    }
}
