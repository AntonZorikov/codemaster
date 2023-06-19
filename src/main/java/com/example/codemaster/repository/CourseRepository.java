package com.example.codemaster.repository;

import com.example.codemaster.entity.CourseEntity;
import com.example.codemaster.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CourseRepository extends CrudRepository<CourseEntity, Long> {
    CourseEntity findByTitle(String name);
    boolean existsByTitle(String title);
    ArrayList<CourseEntity> findAllByAuthorId(Long authorId);
    ArrayList<CourseEntity> findByPublishedFalse();
    ArrayList<CourseEntity> findByTitleContaining(String keyword);
    @Query("SELECT c FROM CourseEntity c WHERE (SELECT AVG(r.grade) FROM RatingEntity r WHERE r.courseId = c.id) > 0 ORDER BY (SELECT AVG(r.grade) FROM RatingEntity r WHERE r.courseId = c.id) DESC")
    ArrayList<CourseEntity> findTopRatedCourses();

}
