package com.nexus.controller;

import com.nexus.model.Task;
import com.nexus.model.TaskStatus;
import com.nexus.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {


    @GetMapping("/tasks")
    public ResponseEntity<String> welcomeTask() {
        return new ResponseEntity<>("Welcome to Task Service", HttpStatus.OK);
    }
}
