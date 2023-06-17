package com.example.codemaster.service;

import com.example.codemaster.entity.CourseEntity;
import com.example.codemaster.entity.RatingEntity;
import com.example.codemaster.entity.UserEntity;
import com.example.codemaster.exception.CommentaryAlreadyExist;
import com.example.codemaster.exception.CourseAlreadyExists;
import com.example.codemaster.exception.CourseNotFound;
import com.example.codemaster.repository.CartRepository;
import com.example.codemaster.repository.CourseRepository;
import com.example.codemaster.repository.RatingRepository;
import com.example.codemaster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private CartRepository cartRepository;

    public CourseEntity createCourse(CourseEntity courseEntity, Long authorId) throws CourseAlreadyExists {
        UserEntity user = userRepository.findById(authorId).get();
        courseEntity.setAuthor(user);
        if (courseRepository.existsByTitle(courseEntity.getTitle())) {
            throw new CourseAlreadyExists("Course already exists");
        } else {
            return courseRepository.save(courseEntity);
        }
    }

    public CourseEntity getCourse(Long id) throws CourseNotFound {
        if(courseRepository.findById(id) == null){
            throw new CourseNotFound("Course not found");
        }
        else{
            return courseRepository.findById(id).get();
        }
    }

    public CourseEntity updateCourse(Long id, CourseEntity courseEntity) throws CourseNotFound{
        Optional<CourseEntity> optionalCourse = courseRepository.findById(id);
        if(optionalCourse.isPresent()){
            CourseEntity course = optionalCourse.get();
            course.setDescription(courseEntity.getDescription());
            course.setTitle(courseEntity.getTitle());
            course.setPrice(courseEntity.getPrice());
            course.setPublished(courseEntity.isPublished());
            return courseRepository.save(course);
        }
        else{
            throw new CourseNotFound("Course not found");
        }
    }

    public ArrayList<CourseEntity> getAllCoursesBuAuthorId(Long authorId){
        return courseRepository.findAllByAuthorId(authorId);
    }

    public ArrayList<CourseEntity> getAllByPublishedFalse(){
        return courseRepository.findByPublishedFalse();
    }
    public ArrayList<CourseEntity> findByTitleContaining(String string){
        return courseRepository.findByTitleContaining(string);
    }

    public RatingEntity createRating(RatingEntity ratingEntity) throws CommentaryAlreadyExist {
        RatingEntity foundRatingEntity = ratingRepository.findByCourseIdAndUserId(ratingEntity.getCourseId(), ratingEntity.getUserId());
        if (foundRatingEntity != null) {
            throw new CommentaryAlreadyExist("Commentary already exists");
        } else {
            return ratingRepository.save(ratingEntity);
        }
    }

    public ArrayList<RatingEntity> getAllRatingByCourseId(Long courseId){
        return ratingRepository.findAllByCourseId(courseId);
    }

    public ArrayList<RatingEntity> getAllByCourseIdWhereCommentaryNotNull(Long courseId){
        return ratingRepository.findAllByCourseIdWhereCommentaryNotNull(courseId);
    }
}
