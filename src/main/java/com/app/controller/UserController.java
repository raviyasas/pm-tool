package com.app.controller;

import com.app.model.common.ApiResponse;
import com.app.model.request.AssignIssueRequest;
import com.app.model.request.UserRequest;
import com.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserRequest userRequest){
        logger.info("Create user API: {}", userRequest);
        return userService.saveUser(userRequest);
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getUsers() {
        logger.info("Retrieve all Users API");
        return userService.getAll();
    }

    @PutMapping("/issues/assign")
    public ResponseEntity<ApiResponse> assignIssue(@RequestBody AssignIssueRequest assignIssueRequest){
        logger.info("Assign issue API: {}", assignIssueRequest);
        return userService.assignIssue(assignIssueRequest);
    }
}
