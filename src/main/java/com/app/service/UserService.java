package com.app.service;

import com.app.model.common.ApiResponse;
import com.app.model.request.AssignIssueRequest;
import com.app.model.request.UserRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponse> saveUser(UserRequest userRequest);

    ResponseEntity<ApiResponse> assignIssue(AssignIssueRequest assignIssueRequest);

    ResponseEntity<ApiResponse> getAll();
}
