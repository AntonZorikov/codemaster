package com.example.codemaster.service;

import com.example.codemaster.entity.CartEntity;
import com.example.codemaster.entity.UserEntity;
import com.example.codemaster.exception.CourseAlreadyPurchased;
import com.example.codemaster.repository.CartRepository;
import com.example.codemaster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    public UserService(UserRepository userRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }


    public UserEntity getUserById(Long userId){
        return userRepository.findById(userId).get();
    }

    public void addToTheCart(CartEntity cartEntity) throws CourseAlreadyPurchased {
        if (cartRepository.findByCourseIdAndUserId(cartEntity.getCourseId(), cartEntity.getUserId()) != null) {
            throw new CourseAlreadyPurchased("Course already purchased");
        } else {
            cartRepository.save(cartEntity);
        }
    }

    public boolean findCourseInCart(Long userId, Long courseId) throws CourseAlreadyPurchased {
        return cartRepository.findByCourseIdAndUserId(courseId, userId) != null;
    }

    public ArrayList<CartEntity> findAllCourseInCartByUserId(Long userId){
        return cartRepository.findAllByUserId(userId);
    }
}
