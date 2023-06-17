package com.example.codemaster.controller;

import com.example.codemaster.entity.CartEntity;
import com.example.codemaster.entity.CourseEntity;
import com.example.codemaster.entity.RatingEntity;
import com.example.codemaster.exception.*;
import com.example.codemaster.model.CrUpCourseInputs;
import com.example.codemaster.model.RatingForm;
import com.example.codemaster.model.SearchCourseForm;
import com.example.codemaster.service.AuthorizationService;
import com.example.codemaster.service.CourseService;
import com.example.codemaster.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private UserService userService;

    @GetMapping("/getPublishedCourse")
    public String getPublishedCourse(Model model, HttpServletRequest request) throws UserNotAuthorized {
        try {
            HttpSession session = request.getSession();
            Long authorId = (Long) session.getAttribute("userId");

            boolean userIsAuthorize = authorizationService.userIsAuthorize(request);
            if(!userIsAuthorize){
                throw new UserNotAuthorized("UserNotAuthorized");
            }

            model.addAttribute("courses", courseService.getAllCoursesBuAuthorId(authorId));
            return "/my_published_courses";
        } catch (UserNotAuthorized e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "User not authorized");
            return "/my_published_courses";
        }
    }


    @PostMapping("/addCourse")
    public String addCourse(@ModelAttribute CrUpCourseInputs crUpCourseInputs, Model model, HttpServletRequest request) throws UserNotAuthorized {
        try {
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("userId");

            if (userId == null) {
                throw new UserNotAuthorized("UserNotAuthorized");
            }
            courseService.createCourse(new CourseEntity(crUpCourseInputs), userId);
            model.addAttribute("isAdded", true);
            return "/add_course";
        } catch (CourseAlreadyExists e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Course already exists");
            return "/add_course";
        } catch (UserNotAuthorized e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "User not authorized");
            return "/add_course";
        }
    }

    @GetMapping("/getCourse")
    public ResponseEntity getCourse(@RequestParam Long course_id) {
        try {
            return ResponseEntity.ok(courseService.getCourse(course_id));
        } catch (CourseNotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/updateCourse")
    public String updateCourse(@ModelAttribute CrUpCourseInputs crUpCourseInputs, @RequestParam Long courseId,
                               Model model, HttpServletRequest request) throws UserNotAuthorized {
        {
            try {
                HttpSession session = request.getSession();
                Long userId = (Long) session.getAttribute("userId");

                if (userId == null) {
                    throw new UserNotAuthorized("UserNotAuthorized");
                }

                courseService.updateCourse(courseId, new CourseEntity(crUpCourseInputs));

                model.addAttribute("isAdded", true);
                return "/edit_course";
            } catch (UserNotAuthorized e) {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "User not authorized");
                return "/edit_course";
            } catch (CourseNotFound e) {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Course not found");
                return "/edit_course";
            }
        }
    }
    @PostMapping("/searchCourse")
    public String searchCourse(@ModelAttribute SearchCourseForm searchCourseForm, Model model) throws UserNotAuthorized {
        ArrayList<CourseEntity> searchResults = courseService.findByTitleContaining(searchCourseForm.getRequestTitle());
        model.addAttribute("search", true);
        model.addAttribute("search_response", searchResults);
        System.out.println("Find: " + searchResults);

        return "index";
    }

    @PostMapping("/rateCourse")
    public String rateCourse(@RequestParam("courseId") Long courseId, @RequestParam("grade") Long grade,
                             @RequestParam("commentary") String commentary, Model model, HttpServletRequest request)
            throws UserNotAuthorized, CommentaryAlreadyExist {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        boolean userIsAuthorize = authorizationService.userIsAuthorize(request);
        if(!userIsAuthorize){
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "User not authorize");
            return "redirect:/course?courseId=" + courseId;
        }
        RatingEntity ratingEntity = courseService.createRating(new RatingEntity(courseId, commentary, grade, userId));

        return "redirect:/course?courseId=" + courseId;
    }
    @PostMapping("/buyCourse")
    public String buyCourse(@RequestParam("courseId") Long courseId, Model model, HttpServletRequest request) throws CourseAlreadyPurchased {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        boolean userIsAuthorize = authorizationService.userIsAuthorize(request);
        if(!userIsAuthorize){
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "User not authorize");
            return "redirect:/course?courseId=" + courseId;
        }

        userService.addToTheCart(new CartEntity(courseId, userId));

        return "redirect:/course?courseId=" + courseId;
    }
}
