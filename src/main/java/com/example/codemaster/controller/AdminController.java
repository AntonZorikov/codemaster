package com.example.codemaster.controller;

import com.example.codemaster.entity.CourseEntity;
import com.example.codemaster.exception.CourseNotFound;
import com.example.codemaster.exception.NotEnoughRights;
import com.example.codemaster.exception.UserNotAuthorized;
import com.example.codemaster.service.AuthorizationService;
import com.example.codemaster.service.CourseService;
import com.example.codemaster.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping("/accept_application")
    public String acceptApplication(@RequestParam Long courseId, HttpServletRequest request, Model model) throws NotEnoughRights {
        try {
            boolean userIsAuthorize = authorizationService.userIsAuthorize(request);
            if(!userIsAuthorize){
                throw new UserNotAuthorized("UserNotAuthorized");
            }

            CourseEntity courseEntity = courseService.getCourse(courseId);
            courseEntity.setPublished(true);
            courseService.updateCourse(courseId, courseEntity);
            model.addAttribute("courses", courseService.getAllByPublishedFalse());
            return "accept_application";
        }
        catch (UserNotAuthorized e){
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "User not authorized");
            return "accept_application";
        }
        catch (CourseNotFound e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Course not found");
            return "accept_application";
        }
    }

}
