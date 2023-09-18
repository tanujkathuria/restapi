package com.developersworld.restapi.controller;

import com.developersworld.restapi.entity.User;
import com.developersworld.restapi.exception.UserNotFoundException;
import com.developersworld.restapi.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userRepository.findAll();
        if(users.size() == 0)
            throw new UserNotFoundException("user is not found");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user is not found"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        User newUser  = userRepository.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable int id){
        User newUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user is not found"));
        newUser.setUserName(user.getUserName());
        newUser.setPassword(user.getPassword());
        return new ResponseEntity<>(userRepository.save(newUser), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id){
        User newUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user is not found"));
        userRepository.delete(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

}
