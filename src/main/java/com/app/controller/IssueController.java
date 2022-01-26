package com.app.controller;

import com.app.model.common.ApiResponse;
import com.app.model.request.IssueRequest;
import com.app.model.request.ProjectRequest;
import com.app.service.IssueService;
import com.app.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class IssueController {

    private static final Logger logger = LoggerFactory.getLogger(IssueController.class);

    @Autowired
    private IssueService issueService;

    @PostMapping("/issues")
    public ResponseEntity<ApiResponse> createIssue(@RequestBody IssueRequest issueRequest) {
        logger.info("Create issue API: {}", issueRequest);
        return issueService.saveIssue(issueRequest);
    }

    @GetMapping("/issues")
    public ResponseEntity<ApiResponse> getIssues() {
        logger.info("Retrieve all issues API");
        return issueService.getAll();
    }

    @GetMapping("/issues/{id}")
    private ResponseEntity<ApiResponse> getIssue(@PathVariable Integer id) {
        logger.info("Retrieve issue API for issueId: {}", id);
        return issueService.getIssue(id);
    }
}
