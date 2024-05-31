package com.example.spring_basic_auth.controller;

import com.example.spring_basic_auth.model.User;
import com.example.spring_basic_auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    // need to create service for this -> just for testing only
    @Autowired private UserRepository repository;
    @GetMapping
    public List<User> getUsers () {
        return repository.findAll();
    }
}
