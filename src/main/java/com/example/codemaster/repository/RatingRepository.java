package com.example.codemaster.repository;

import com.example.codemaster.entity.RatingEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface RatingRepository extends CrudRepository<RatingEntity, Long> {
    RatingEntity findByCourseIdAndUserId(Long courseId, Long userId);
    ArrayList<RatingEntity> findAllByCourseId(Long courseId);
}
