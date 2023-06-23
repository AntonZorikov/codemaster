package com.example.codemaster.entity;

import com.example.codemaster.model.RatingForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rating")
@Getter
@Setter
@NoArgsConstructor
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
}
