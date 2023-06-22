package com.example.codemaster.service;

import com.example.codemaster.entity.CartEntity;
import com.example.codemaster.entity.UserEntity;
import com.example.codemaster.exception.CourseAlreadyPurchased;
import com.example.codemaster.repository.CartRepository;
import com.example.codemaster.repository.UserRepository;
import com.example.codemaster.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    private UserService userService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, cartRepository);
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        UserEntity result = userService.getUserById(userId);

        verify(userRepository).findById(userId);
        assertEquals(userEntity, result);
    }

    @Test
    public void testFindCourseInCart() throws CourseAlreadyPurchased {
        Long userId = 1L;
        Long courseId = 1L;
        CartEntity cartEntity = new CartEntity();
        cartEntity.setCourseId(courseId);
        cartEntity.setUserId(userId);
        when(cartRepository.findByCourseIdAndUserId(userId, courseId)).thenReturn(cartEntity);

        boolean result = userService.findCourseInCart(userId, courseId);

        verify(cartRepository).findByCourseIdAndUserId(courseId, userId);
        assertTrue(result);
    }

    @Test
    public void testFindAllCourseInCartByUserId(){
        Long userId = 1L;
        ArrayList<CartEntity> expected = new ArrayList<>();
        when(cartRepository.findAllByUserId(userId)).thenReturn(expected);

        ArrayList<CartEntity> result = userService.findAllCourseInCartByUserId(userId);

        verify(cartRepository).findAllByUserId(userId);

        assertSame(expected, result);
    }
}
