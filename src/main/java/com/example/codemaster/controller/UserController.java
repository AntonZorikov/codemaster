package com.example.codemaster.controller;

import com.example.codemaster.entity.UserEntity;
import com.example.codemaster.exception.IncorrectPassword;
import com.example.codemaster.exception.UserAlreadyExist;
import com.example.codemaster.exception.UserNotFound;
import com.example.codemaster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserEntity user){
        try{
            userService.login(user);
            return ResponseEntity.ok("User login successful");
        }
        catch (UserAlreadyExist e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("signin")
    public ResponseEntity signin(@RequestBody UserEntity userEntity){
        try{
            return ResponseEntity.ok(userService.signin(userEntity));
        }
        catch (UserNotFound | IncorrectPassword e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
