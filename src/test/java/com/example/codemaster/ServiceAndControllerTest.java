package com.example.codemaster;

import com.example.codemaster.controller.*;
import com.example.codemaster.service.AuthorizationService;
import com.example.codemaster.service.CourseService;
import com.example.codemaster.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = CodemasterApplication.class)
public class ServiceAndControllerTest {
    @Value(value="${local.server.port}")
    private int port;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminController adminController;
    @Autowired
    private AdminPageController adminPageController;
    @Autowired
    private AuthorizationController authorizationController;
    @Autowired
    private CourseController courseController;
    @Autowired
    private PageController pageController;
    @Autowired
    private UploadController uploadController;

    @Test
    public void  NotNulTest() throws Exception {
        assertThat(authorizationService).isNotNull();
        assertThat(courseService).isNotNull();
        assertThat(userService).isNotNull();
        assertThat(adminController).isNotNull();
        assertThat(adminPageController).isNotNull();
        assertThat(authorizationController).isNotNull();
        assertThat(courseController).isNotNull();
        assertThat(pageController).isNotNull();
        assertThat(uploadController).isNotNull();
    }
}
