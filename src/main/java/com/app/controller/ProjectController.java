package com.app.controller;

import com.app.model.common.ApiResponse;
import com.app.model.request.ProjectRequest;
import com.app.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @PostMapping("/projects")
    public ResponseEntity<ApiResponse> createProject(@RequestBody ProjectRequest projectRequest) {
        logger.info("Create project API: {}", projectRequest);
        return projectService.saveProject(projectRequest);
    }

    @GetMapping("/projects")
    public ResponseEntity<ApiResponse> getProjects() {
        logger.info("Retrieve all projects API");
        return projectService.getAll();
    }

    @GetMapping("/projects/{id}")
    private ResponseEntity<ApiResponse> getProject(@PathVariable Integer id) {
        logger.info("Retrieve project API for projectId: {}", id);
        return projectService.getProject(id);
    }

}
