package com.example.spring_basic_auth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/user")
    public String getUsers (@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        String roles = userDetails.getAuthorities().toString();

        return "Username: " + username + ", Roles: " + roles;
    }
}
