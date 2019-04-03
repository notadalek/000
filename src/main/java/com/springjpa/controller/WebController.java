package com.springjpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.springjpa.model.User;
import com.springjpa.repo.UserRepository;

@RestController
public class WebController {
    @Autowired
    UserRepository repository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody User user) {
        String passFromDB = "";

        User u = repository.findByUsername(user.getUsername());

        if (u != null) {
            passFromDB = u.getPassword();
            if (!user.getPassword().equals(passFromDB)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } else if (user.getPassword().equals(passFromDB)) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }

        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}




