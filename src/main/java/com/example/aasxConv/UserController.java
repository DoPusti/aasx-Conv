package com.example.aasxConv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    private ResponseEntity<User> register(@RequestBody User newUser) {
        userRepository.save(newUser);
        return new ResponseEntity<User>(newUser, HttpStatus.OK);
    }

    @GetMapping("/user")
    private ResponseEntity<User> register(@RequestParam(value = "id")int id) {
        var user = userRepository.findById(id);
        if(user.isPresent()) {
            return new ResponseEntity<User>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity("No user found with id " + id,HttpStatus.NOT_FOUND);

    }

}
