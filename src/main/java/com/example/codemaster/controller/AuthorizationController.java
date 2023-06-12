package com.example.codemaster.controller;

import com.example.codemaster.entity.UserEntity;
import com.example.codemaster.exception.IncorrectPassword;
import com.example.codemaster.exception.UserAlreadyExist;
import com.example.codemaster.exception.UserNotFound;
import com.example.codemaster.model.LogInInputs;
import com.example.codemaster.model.SignInInputs;
import com.example.codemaster.service.AuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthorizationController {

    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/login")
    public String signin(@ModelAttribute LogInInputs logInInputs, Model model) {
        try{
            UserEntity user = authorizationService.login(new UserEntity(logInInputs.username, logInInputs.password));
            model.addAttribute("loggedIn", true);
            return "/login";
        }
        catch (UserAlreadyExist e){
            model.addAttribute("error", true);
            return "/login";        }
    }
    @PostMapping("/signin")
    public String signin(@ModelAttribute SignInInputs signinInputs, Model model, HttpServletRequest request) {
        try{
            UserEntity user = authorizationService.signin(signinInputs.username, signinInputs.password);

            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getName());
            session.setAttribute("loggedIn", true);

            model.addAttribute("loggedIn", true);
            return "/signin";
        }
        catch (UserNotFound | IncorrectPassword e){
            model.addAttribute("error", true);
            return "/signin";
        }
    }
}
