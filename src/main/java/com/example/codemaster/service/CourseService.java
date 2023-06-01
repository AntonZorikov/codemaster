package com.example.codemaster.service;

import com.example.codemaster.entity.CourseEntity;
import com.example.codemaster.entity.UserEntity;
import com.example.codemaster.exception.CourseAlreadyExists;
import com.example.codemaster.exception.CourseNotFound;
import com.example.codemaster.repository.CourseRepository;
import com.example.codemaster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

//    public Optional<CourseEntity> getCours(Long id) throws CourseNotFound {
//        if(coursRepository.findById(id) == null){
//            throw new CourseNotFound("Cours not found");
//        }
//        else{
//            return coursRepository.findById(id);
//        }
//    }
}
