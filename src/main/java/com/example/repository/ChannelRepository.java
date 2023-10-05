package com.example.repository;

import com.example.entity.ChannelEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelEntity, String> {

    @Transactional
    @Modifying
    @Query("update ChannelEntity  as p set  p.name=:name,p.description=:description where p.id=:id")
    int update(@Param("id") String id, @Param("name") String name, @Param("description") String description);

    Optional<ChannelEntity> findByName(String name);

    @Transactional
    @Modifying
    @Query("update ChannelEntity  as p set  p.photoId=:photoId where p.id=:id")
    int updatePhoto(@Param("id") String id, @Param("photoId") String photoId);

    @Transactional
    @Modifying
    @Query("update ChannelEntity  as p set  p.bannerId=:bannerId where p.id=:id")
    int updateBanner(@Param("id") String  id, @Param("bannerId") String bannerId);


    Page<ChannelEntity> findAll(Pageable pageable);

    Optional<ChannelEntity> findByProfileId(Integer id);

    Iterable<ChannelEntity> findAllByProfileId(Integer id);
}