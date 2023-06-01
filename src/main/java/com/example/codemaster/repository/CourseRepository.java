package com.example.codemaster.repository;

import com.example.codemaster.entity.CourseEntity;
import com.example.codemaster.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<CourseEntity, Long> {
    UserEntity findByTitle(String name);

    boolean existsByTitle(String title);
}
