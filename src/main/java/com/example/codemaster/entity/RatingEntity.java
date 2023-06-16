package com.example.codemaster.entity;

import com.example.codemaster.model.RatingForm;
import jakarta.persistence.*;

@Entity
@Table(name = "rating")
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "user_id")
    private Long userId;

    @Column(columnDefinition = "TEXT", name = "commentary")
    private String commentary;

    private Long grade;

    public RatingEntity() {
    }

    public RatingEntity(RatingForm ratingForm, Long userId) {
        this.courseId = ratingForm.courseId;
        this.commentary = ratingForm.commentary;
        this.grade = ratingForm.grade;
        this.userId = userId;
    }

    public RatingEntity(Long courseId, String commentary, Long grade, Long userId) {
        this.courseId = courseId;
        this.commentary = commentary;
        this.grade = grade;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }
}
