package com.sairuchithaht.pomodorotimer.controller;

import com.sairuchithaht.pomodorotimer.model.User;
import com.sairuchithaht.pomodorotimer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null){
            return ResponseEntity.badRequest().body("User with this email already exists");
        } else if(userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("User with this username already exists");
        }

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        if (password.equals(user.getPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}
