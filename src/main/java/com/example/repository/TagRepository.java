package com.example.repository;

import com.example.entity.TagEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update TagEntity  as r set  r.name=:name where r.id=:id")
    int update(@Param("id") Integer id, @Param("name") String name);
}
