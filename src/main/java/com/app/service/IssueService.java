package com.app.service;

import com.app.model.common.ApiResponse;
import com.app.model.request.IssueRequest;
import org.springframework.http.ResponseEntity;

public interface IssueService {
    ResponseEntity<ApiResponse> saveIssue(IssueRequest issueRequest);

    ResponseEntity<ApiResponse> getAll();

    ResponseEntity<ApiResponse> getIssue(Integer id);

    ResponseEntity<ApiResponse> updateIssue(Integer id, IssueRequest issueRequest);

    ResponseEntity<ApiResponse> getIssuesByProject(Integer projectId);
}
