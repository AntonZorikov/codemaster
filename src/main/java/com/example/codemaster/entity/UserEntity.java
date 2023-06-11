package com.example.codemaster.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.annotations.ColumnDefault;
import java.util.ArrayList;
import java.util.List;
import com.example.codemaster.entity.CourseEntity;


@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(25)")
    private String name;

    @JsonIgnore
    @Column(columnDefinition = "varchar(60)")
    private String password;

    @Column(columnDefinition = "varchar(10)")
    private String role;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseEntity> courses;

    public UserEntity() {

    }

    public UserEntity(String name, String password) {
        this.name = name;
        this.password = password;
        this.role = "user";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<CourseEntity> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseEntity> courses) {
        this.courses = courses;
    }
}
