package com.example.codemaster.controller;

import com.example.codemaster.entity.CartEntity;
import com.example.codemaster.entity.CourseEntity;
import com.example.codemaster.entity.RatingEntity;
import com.example.codemaster.exception.CourseAlreadyPurchased;
import com.example.codemaster.exception.CourseNotFound;
import com.example.codemaster.exception.UserNotAuthorized;
import com.example.codemaster.service.AuthorizationService;
import com.example.codemaster.service.CourseService;
import com.example.codemaster.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.DecimalFormat;

import java.util.ArrayList;

@Controller
public class PageController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String hello() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/signin")
    public String signin() {
        return "signin";
    }

    @RequestMapping("/addcourse")
    public String addCourse() {
        return "add_course";
    }

    @RequestMapping("/my_published_courses")
    public String myPublishedCourse(Model model, HttpServletRequest request) throws UserNotAuthorized {
        try {
            HttpSession session = request.getSession();
            Long authorId = (Long) session.getAttribute("userId");

            boolean userIsAuthorize = authorizationService.userIsAuthorize(request);
            if (!userIsAuthorize) {
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

    @RequestMapping("edit_course")
    public String editCourse(@RequestParam Long courseId, Model model, HttpServletRequest request) throws UserNotAuthorized {
        try {
            HttpSession session = request.getSession();
            Long authorId = (Long) session.getAttribute("userId");
            boolean userIsAuthorize = authorizationService.userIsAuthorize(request);
            if (!userIsAuthorize) {
                throw new UserNotAuthorized("UserNotAuthorized");
            }

            if (courseService.getAllCoursesBuAuthorId(authorId).contains(courseService.getCourse(courseId))) {
                model.addAttribute("courseId", courseId);
                model.addAttribute("description", courseService.getCourse(courseId).getDescription());
                return "/edit_course";
            } else {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Course not found");
                return "/edit_course";
            }
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

    @RequestMapping("/uploads")
    public String uploads() {
        return "uploads";
    }

    @RequestMapping("/course")
    public String course(@RequestParam Long courseId, Model model, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("userId");
            boolean userIsAuthorize = authorizationService.userIsAuthorize(request);
            if (courseService.getCourse(courseId).isPublished()) {
                ArrayList<RatingEntity> allRating = courseService.getAllRatingByCourseId(courseId);
                Long sum = allRating.stream().mapToLong(RatingEntity::getGrade).sum();
                float avg = allRating.isEmpty() ? 0 : (float) sum / allRating.size();
                float roundedAvg = Math.round(avg * 10) / 10.0f;

                model.addAttribute("course", courseService.getCourse(courseId));
                model.addAttribute("courseId", courseId);
                model.addAttribute("authorize",     true);
                if(avg > 0) {
                    model.addAttribute("rating", true);
                    model.addAttribute("avgRating", roundedAvg);
                }

                if(userIsAuthorize && userService.findCourseInCart(userId, courseId)){
                    model.addAttribute("isBuy", true);
                }

                if(userIsAuthorize && !userService.findCourseInCart(userId, courseId)){
                    model.addAttribute("notBuy", true);
                }

                model.addAttribute("commentary", courseService.getAllByCourseIdWhereCommentaryNotNull(courseId));
                return "/course";
            } else {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Course not found");
                return "/course";
            }
        } catch (CourseNotFound e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Course not found");
            return "/course";
        } catch (CourseAlreadyPurchased e) {
            throw new RuntimeException(e);
        }
    }
    @RequestMapping("/cart")
    public String cart(Model model, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("userId");
            boolean userIsAuthorize = authorizationService.userIsAuthorize(request);
            if (!userIsAuthorize) {
                throw new UserNotAuthorized("UserNotAuthorized");
            }
            ArrayList<CartEntity> coursesInCart = userService.findAllCourseInCartByUserId(userId);
            ArrayList<CourseEntity> courses = new ArrayList<>();
            for(int i = 0; i < coursesInCart.size();i++){
                courses.add(courseService.getCourse(coursesInCart.get(i).getCourseId()));
            }
            model.addAttribute("courses", courses);
            return "/cart";
        }
        catch (UserNotAuthorized e){
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "User not authorized");
            return "/cart";
        } catch (CourseNotFound e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Error");
            return "/cart";
        }
    }
}
