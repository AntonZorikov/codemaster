package com.example.codemaster.service;

import com.example.codemaster.entity.UserEntity;
import com.example.codemaster.exception.IncorrectPassword;
import com.example.codemaster.exception.UserAlreadyExist;
import com.example.codemaster.exception.UserNotFound;
import com.example.codemaster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity login(UserEntity userEntity) throws UserAlreadyExist {
        if (userRepository.findByName(userEntity.getName()) == null){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userEntity.setPassword(encoder.encode(userEntity.getPassword()));
            //boolean matches = encoder.matches("password", hashedPassword);
            return userRepository.save(userEntity);
        }
        else {
            throw new UserAlreadyExist("User already exist");
        }
    }

    public UserEntity signin(UserEntity userEntity) throws UserNotFound, IncorrectPassword {
        UserEntity existingUser = userRepository.findByName(userEntity.getName());
        if (existingUser == null) {
            throw new UserNotFound("User not found");
        }
        System.out.println(userEntity.getPassword());
        System.out.println(existingUser.getPassword());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(userEntity.getPassword(), existingUser.getPassword())) {
            return existingUser;
        } else {
            throw new IncorrectPassword("Incorrect password");
        }
    }


}
