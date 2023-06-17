package com.example.codemaster.service;

import com.example.codemaster.entity.UserEntity;
import com.example.codemaster.exception.IncorrectPassword;
import com.example.codemaster.exception.UserAlreadyExist;
import com.example.codemaster.exception.UserNotFound;
import com.example.codemaster.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    @Autowired
    private UserRepository userRepository;


    public UserEntity login(UserEntity userEntity) throws UserAlreadyExist {
        if (userRepository.findByName(userEntity.getName()) == null){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userEntity.setPassword(encoder.encode(userEntity.getPassword()));
            return userRepository.save(userEntity);
        }
        else {
            throw new UserAlreadyExist("User already exist");
        }
    }

    public UserEntity signin(String login, String password) throws UserNotFound, IncorrectPassword {
        UserEntity existingUser = userRepository.findByName(login);
        if (existingUser == null) {
            throw new UserNotFound("User not found");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(password, existingUser.getPassword())) {
            return existingUser;
        } else {
            throw new IncorrectPassword("Incorrect password");
        }
    }

    public boolean userIsAuthorize(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        return userId != null;
    }

}
