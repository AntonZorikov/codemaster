package com.example.codemaster.service;

import com.example.codemaster.entity.UserEntity;
import com.example.codemaster.exception.UserAlreadyExist;
import com.example.codemaster.exception.UserNotFound;
import com.example.codemaster.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthorizationServiceTest {

    @Mock
    UserRepository userRepository;

    private AuthorizationService authorizationService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        authorizationService = new AuthorizationService(userRepository);
    }

    @Test
    public void testLogin() throws UserAlreadyExist {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("qwerty");
        userEntity.setName("qwerty");

        when(userRepository.save(userEntity)).thenReturn(userEntity);

        UserEntity result = authorizationService.login(userEntity);

        verify(userRepository).save(userEntity);

        assertEquals(userEntity, result);
    }

    @Test
    public void testSignin() throws UserNotFound {
        String login = "qwerty";
        String password = "qwerty";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        UserEntity expected = new UserEntity();
        expected.setName(login);
        expected.setPassword(encoder.encode(password));

        when(userRepository.findByName(login)).thenReturn(expected);

        UserEntity result = authorizationService.signin(login, password);

        verify(userRepository).findByName(login);

        assertEquals(expected, result);
    }


}