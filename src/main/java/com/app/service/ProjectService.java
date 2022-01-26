package com.app.service;

import com.app.model.common.ApiResponse;
import com.app.model.request.ProjectRequest;
import org.springframework.http.ResponseEntity;

public interface ProjectService {
    ResponseEntity<ApiResponse> saveProject(ProjectRequest projectRequest);

    ResponseEntity<ApiResponse> getAll();

    ResponseEntity<ApiResponse> getProject(Integer id);
}
