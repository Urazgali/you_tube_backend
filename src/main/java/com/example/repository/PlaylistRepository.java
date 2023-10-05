package com.example.repository;

import com.example.entity.PlaylistEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends CrudRepository<PlaylistEntity,Integer>, PagingAndSortingRepository<PlaylistEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update PlaylistEntity  as r set  r.name=:name where r.id=:id")
    int update(@Param("id") Integer id, @Param("name") String name);

     Page<PlaylistEntity> findAll(Pageable pageable);
}
