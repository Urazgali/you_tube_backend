package com.example.repository;

import com.example.entity.CommentLikeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity,String>,
        PagingAndSortingRepository<CommentLikeEntity,String> {
}
