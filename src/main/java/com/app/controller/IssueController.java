package com.app.controller;

import com.app.model.common.ApiResponse;
import com.app.model.request.IssueRequest;
import com.app.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class IssueController {

    private static final Logger logger = LoggerFactory.getLogger(IssueController.class);

    @Autowired
    private IssueService issueService;

    @PostMapping("/issues")
    public ResponseEntity<ApiResponse> createIssue(@RequestBody IssueRequest issueRequest) {
        logger.info("Create issue API: {}", issueRequest);
        return issueService.saveIssue(issueRequest);
    }

    @PutMapping("/issues/{id}")
    public ResponseEntity<ApiResponse> updateIssue(@PathVariable Integer id, @RequestBody IssueRequest issueRequest) {
        logger.info("Update issue API: {}, {}", id, issueRequest);
        return issueService.updateIssue(id, issueRequest);
    }

    @GetMapping("/issues")
    public ResponseEntity<ApiResponse> getIssues() {
        logger.info("Retrieve all issues API");
        return issueService.getAll();
    }

    @GetMapping("/issues/{id}")
    public ResponseEntity<ApiResponse> getIssue(@PathVariable Integer id) {
        logger.info("Retrieve issue API for issueId: {}", id);
        return issueService.getIssue(id);
    }

    @GetMapping("/issues/project/{projectId}")
    public ResponseEntity<ApiResponse> getIssueByProject(@PathVariable Integer projectId) {
        logger.info("Retrieve issues for projectId: {}", projectId);
        return issueService.getIssuesByProject(projectId);
    }


}
