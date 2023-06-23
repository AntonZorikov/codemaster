package com.example.codemaster.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import java.util.ArrayList;
import java.util.List;
import com.example.codemaster.entity.CourseEntity;


@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
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

    public UserEntity(String name, String password) {
        this.name = name;
        this.password = password;
        this.role = "user";
    }
}
