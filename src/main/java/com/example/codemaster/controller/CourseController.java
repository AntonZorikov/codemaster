package com.example.codemaster.controller;

import com.example.codemaster.entity.CourseEntity;
import com.example.codemaster.entity.UserEntity;
import com.example.codemaster.exception.CourseAlreadyExists;
import com.example.codemaster.exception.CourseNotFound;
import com.example.codemaster.repository.UserRepository;
import com.example.codemaster.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
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

    @GetMapping("/get")
    public ResponseEntity getCourse(@RequestParam Long course_id){
        try{
             return ResponseEntity.ok(courseService.getCourse(course_id));
        }
        catch (CourseNotFound e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateCourse(@RequestBody CourseEntity courseEntity, @RequestParam Long course_id)
    {
        try{
            return ResponseEntity.ok(courseService.updateCourse(course_id, courseEntity));
        }
        catch (CourseNotFound e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
