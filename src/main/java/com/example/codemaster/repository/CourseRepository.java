package com.example.codemaster.repository;

import com.example.codemaster.entity.CourseEntity;
import com.example.codemaster.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CourseRepository extends CrudRepository<CourseEntity, Long> {
    UserEntity findByTitle(String name);
    boolean existsByTitle(String title);
    ArrayList<CourseEntity> findAllByAuthorId(Long authorId);
    ArrayList<CourseEntity> findByPublishedFalse();
    ArrayList<CourseEntity> findByTitleContaining(String keyword);
}
