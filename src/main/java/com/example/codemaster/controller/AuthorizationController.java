package com.example.codemaster.controller;

import com.example.codemaster.entity.UserEntity;
import com.example.codemaster.exception.IncorrectPassword;
import com.example.codemaster.exception.UserAlreadyExist;
import com.example.codemaster.exception.UserNotFound;
import com.example.codemaster.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {

    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserEntity user){
        try{
            authorizationService.login(user);
            return ResponseEntity.ok("User login successful");
        }
        catch (UserAlreadyExist e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/signin")
    public ResponseEntity signin(@RequestParam(name = "login") String login, @RequestParam(name = "password") String password){
        try{
            return ResponseEntity.ok(authorizationService.signin(login, password));
        }
        catch (UserNotFound | IncorrectPassword e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
