package com.example.codemaster.service;

import com.example.codemaster.entity.UserEntity;
import com.example.codemaster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity getUserById(Long userId){
        return userRepository.findById(userId).get();
    }
}
