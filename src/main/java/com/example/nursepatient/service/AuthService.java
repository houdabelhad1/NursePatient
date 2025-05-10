package com.example.nursepatient.service;

import com.example.nursepatient.entity.User;
import com.example.nursepatient.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepo;
    private User userLoggedIn;

    public boolean login(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                userLoggedIn = user;
                return true;
            }
        }
        return false;
    }
    public User getCurrentUser(){return  userLoggedIn;}
}
