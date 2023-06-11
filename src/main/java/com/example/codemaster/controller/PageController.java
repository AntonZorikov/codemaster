package com.example.codemaster.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
//    @RequestMapping("/")
//    public String hello(@RequestParam(value = "name", required = true) String name, Model model){
//        model.addAttribute("name", name);
//        return "index";
//    }
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
}
