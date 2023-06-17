package com.example.codemaster.repository;

import com.example.codemaster.entity.CartEntity;
import com.example.codemaster.entity.RatingEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CartRepository extends CrudRepository<CartEntity, Long> {
    CartEntity findByCourseIdAndUserId(Long courseId, Long userId);

}

