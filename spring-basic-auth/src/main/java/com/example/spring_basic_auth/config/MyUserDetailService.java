package com.example.spring_basic_auth.config;

import com.example.spring_basic_auth.dto.MyUserDetails;
import com.example.spring_basic_auth.model.User;
import com.example.spring_basic_auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = repository.findByUsername(username);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            return new MyUserDetails(userOptional.get());
        } else {
            throw new UsernameNotFoundException("Not found username");
        }

    }
}
