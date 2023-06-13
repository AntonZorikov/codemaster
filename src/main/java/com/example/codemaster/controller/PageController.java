package com.example.codemaster.controller;

import com.example.codemaster.exception.CourseNotFound;
import com.example.codemaster.exception.UserNotAuthorized;
import com.example.codemaster.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
    @Autowired
    private CourseService courseService;

    @RequestMapping("/")
    public String hello(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/signin")
    public String signin(){
        return "signin";
    }

    @RequestMapping("/addcourse")
    public String addCourse(){
        return "add_course";
    }

    @RequestMapping("/my_published_courses")
    public String myPublishedCourse(Model model, HttpServletRequest request) throws UserNotAuthorized {
        try{
            HttpSession session = request.getSession();
            Long authorId = (Long) session.getAttribute("userId");
            if(authorId == null){
                throw new UserNotAuthorized("UserNotAuthorized");
            }
            model.addAttribute("courses", courseService.getAllCoursesBuAuthorId(authorId));
            return "/my_published_courses";
        }
        catch (UserNotAuthorized e){
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "User not authorized");
            return "/my_published_courses";
        }
    }

    @RequestMapping("edit_course")
    public String editCourse(@RequestParam Long courseId, Model model, HttpServletRequest request) throws UserNotAuthorized{
        try{
            HttpSession session = request.getSession();
            Long authorId = (Long) session.getAttribute("userId");
            if(authorId == null){
                throw new UserNotAuthorized("UserNotAuthorized");
            }
            if(courseService.getAllCoursesBuAuthorId(authorId).contains(courseService.getCourse(courseId))){
                model.addAttribute("courseId", courseId);
                model.addAttribute("description", courseService.getCourse(courseId).getDescription());
                return "/edit_course";
            }
            else {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Course not found");
                return "/edit_course";
            }
        }
        catch (UserNotAuthorized e){
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
    public String uploads(){
        return "uploads";
    }

}
