package com.example.codemaster.repository;

import com.example.codemaster.entity.RatingEntity;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<RatingEntity, Long> {
    RatingEntity findByCourseIdAndUserId(Long courseId, Long userId);
}
