package com.example.codemaster.controller;

import com.example.codemaster.exception.NotEnoughRights;
import com.example.codemaster.exception.UserNotAuthorized;
import com.example.codemaster.service.CourseService;
import com.example.codemaster.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminPageController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @RequestMapping("/")
    public String adminHomePage(Model model, HttpServletRequest request) throws UserNotAuthorized {
        try {
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                throw new UserNotAuthorized("UserNotAuthorized");
            }
            if (!userService.getUserById(userId).getRole().equals("admin")) {
                throw new NotEnoughRights("NotEnoughRights");
            }
            return "/admin_home_page";
        } catch (UserNotAuthorized e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "User not authorized");
            return "/admin_home_page";
        }
        catch (NotEnoughRights e){
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Not enough rights");
            return "accept_application";
        }
    }
    @RequestMapping("/accept_application")
    public String acceptApplication(Model model, HttpServletRequest request) throws UserNotAuthorized {
        try {
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                throw new UserNotAuthorized("UserNotAuthorized");
            }
            if (!userService.getUserById(userId).getRole().equals("admin")) {
                throw new NotEnoughRights("NotEnoughRights");
            }
            model.addAttribute("courses", courseService.getAllByPublishedFalse());
            return "accept_application";
        } catch (UserNotAuthorized e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "User not authorized");
            return "accept_application";
        }
        catch (NotEnoughRights e){
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Not enough rights");
            return "accept_application";
        }
    }
}
