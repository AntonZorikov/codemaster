package com.example.codemaster.exception;

public class UserNotAuthorized extends Error{
    public UserNotAuthorized(String message) {
        super(message);
    }
}
