package com.example.codemaster.controller;

import com.example.codemaster.entity.CourseEntity;
import com.example.codemaster.entity.UserEntity;
import com.example.codemaster.exception.CourseAlreadyExists;
import com.example.codemaster.repository.UserRepository;
import com.example.codemaster.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    public ResponseEntity addCourse(@RequestBody CourseEntity courseEntity, @RequestParam Long author_id){
        try{
            return ResponseEntity.ok(courseService.createCourse(courseEntity, author_id));
        }
        catch (CourseAlreadyExists e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
