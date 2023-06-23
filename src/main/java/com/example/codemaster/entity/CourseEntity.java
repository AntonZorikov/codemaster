package com.example.codemaster.entity;

import com.example.codemaster.model.CrUpCourseInputs;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(150)")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    private Long price;

    private boolean published;

    public CourseEntity(CrUpCourseInputs crUpCourseInputs) {
        this.title = crUpCourseInputs.getTitle();
        this.description = crUpCourseInputs.getDescription();
        this.price = crUpCourseInputs.getPrice();
        this.published = false;
    }
}
