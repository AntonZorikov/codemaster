package com.example.codemaster.service;

import com.example.codemaster.entity.CourseEntity;
import com.example.codemaster.entity.UserEntity;
import com.example.codemaster.exception.CourseAlreadyExists;
import com.example.codemaster.exception.CourseNotFound;
import com.example.codemaster.repository.CourseRepository;
import com.example.codemaster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository coursRepository;

    @Autowired
    private UserRepository userRepository;

    public CourseEntity createCourse(CourseEntity coursEntity, Long authorId) throws CourseAlreadyExists {
        UserEntity user = userRepository.findById(authorId).get();
        coursEntity.setAuthor(user);
        if (coursRepository.existsByTitle(coursEntity.getTitle())) {
            throw new CourseAlreadyExists("Course already exists");
        } else {
            return coursRepository.save(coursEntity);
        }

    }

    public CourseEntity getCourse(Long id) throws CourseNotFound {
        if(coursRepository.findById(id) == null){
            throw new CourseNotFound("Course not found");
        }
        else{
            return coursRepository.findById(id).get();
        }
    }

    public CourseEntity updateCourse(Long id, CourseEntity courseEntity) throws CourseNotFound{
        Optional<CourseEntity> optionalCourse = coursRepository.findById(id);
        if(optionalCourse.isPresent()){
            CourseEntity course = optionalCourse.get();
            course.setDescription(courseEntity.getDescription());
            course.setTitle(courseEntity.getTitle());
            course.setPrice(courseEntity.getPrice());
            course.setPublished(courseEntity.isPublished());
            return coursRepository.save(course);
        }
        else{
            throw new CourseNotFound("Course not found");
        }
    }

    public ArrayList<CourseEntity> getAllCoursesBuAuthorId(Long authorId){
        ArrayList<CourseEntity> courses = coursRepository.findAllByAuthorId(authorId);
        return courses;
    }
}
