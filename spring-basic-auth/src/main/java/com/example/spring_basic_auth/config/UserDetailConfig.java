package com.example.spring_basic_auth.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Configuration
public class UserDetailConfig {

    private static final Logger LOGGER = Logger.getLogger(UserDetailConfig.class.getName());

    private List<UserDetails> userDetailsList = new ArrayList<>();

    public void init() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mock.properties")) {
            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                    userDetailsList = reader.lines()
                            .map(this::createUserDetails)
                            .collect(Collectors.toList());
                }
            } else {
                LOGGER.warning("mock.properties file not found in classpath");
            }
        } catch (Exception e) {
            LOGGER.severe("Error reading mock.properties file: " + e.getMessage());
        }
    }

    @Bean
    public UserDetailsService userDetailsService() {
        this.init();
        return new InMemoryUserDetailsManager(userDetailsList);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    private UserDetails createUserDetails(String userDetailsLine) {
        String[] userDetailsParts = userDetailsLine.split("=");
        String username = userDetailsParts[0];
        String[] passwordAndRoles = userDetailsParts[1].split(",");
        String password = passwordAndRoles[0];
        String[] roles = new String[passwordAndRoles.length - 1];
        System.arraycopy(passwordAndRoles, 1, roles, 0, roles.length);

        return User.withUsername(username)
                .password(passwordEncoder().encode(password))
                .roles(roles)
                .build();
    }
}
