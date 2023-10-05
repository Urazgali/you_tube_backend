package com.example.repository;

import com.example.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<ReportEntity, String> {
    List<ReportEntity> findAllByPrtId(Integer id);
}
