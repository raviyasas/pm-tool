package com.app.service;

import com.app.exception.DataNotFoundException;
import com.app.model.AppUser;
import com.app.model.ChangeLog;
import com.app.model.Issue;
import com.app.model.Project;
import com.app.model.common.ApiResponse;
import com.app.model.common.CommonResponse;
import com.app.model.request.IssueRequest;
import com.app.repository.ChangeLogRepository;
import com.app.repository.IssueRepository;
import com.app.repository.ProjectRepository;
import com.app.repository.UserRepository;
import com.app.util.CommonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChangeLogRepository changeLogRepository;

    @Autowired
    private CommonResponse commonResponse;

    @Override
    public ResponseEntity<ApiResponse> saveIssue(IssueRequest issueRequest) {

        if (issueRequest == null) {
            throw new DataNotFoundException("Issue data required");
        }

        Optional<Project> project = projectRepository.findById(issueRequest.getProjectId());
        if (project.isEmpty()) {
            throw new DataNotFoundException("Project ID is null or not existing");
        }

        Issue issue = new Issue();
        issue.setCreatedUser(issueRequest.getCreatedUser());
        issue.setIssueState(issueRequest.getIssueState());
        issue.setIssueType(issueRequest.getIssueType());
        issue.setCreatedDate(LocalDateTime.now());
        issue.setIssueDescription(issueRequest.getIssueDescription());
        issue.setProjectId(project.get().getProjectId());
        issue.setProject(project.get());
        issue.setIsAssigned(false);

        issueRepository.save(issue);

        return commonResponse.buildResponse(HttpStatus.CREATED.value(), CommonMessage.SUCCESS.value(),
                CommonMessage.SUCCESS.message());
    }

    @Override
    public ResponseEntity<ApiResponse> updateIssue(Integer id, IssueRequest issueRequest) {

        Optional<Project> project = projectRepository.findById(issueRequest.getProjectId());
        if (project.isEmpty()) {
            throw new DataNotFoundException("Project ID is null or not existing");
        }

        Optional<Issue> existingIssue = issueRepository.findById(id);
        if (existingIssue.isPresent()) {
            List<ChangeLog> changeLogList = existingIssue.get().getChangeLog();

            if(changeLogList == null || changeLogList.isEmpty()){
                changeLogList = new ArrayList<>();
            }

            AppUser user = userRepository.findById(issueRequest.getUserId()).get();

            List<AppUser> users = existingIssue.get().getUsers();
            users.add(user);

            Issue issue = existingIssue.get();
            issue.setIssueState(issueRequest.getChangeLog().getCurrentState());
            issue.setIssueType(issueRequest.getIssueType());
            issue.setIssueDescription(issueRequest.getIssueDescription());
            issue.setUsers(users);
            issue.setIsAssigned(true);
            //issue.setProjectId(project.get().getProjectId());
            issue.setProject(project.get());
            //issue.setChangeLog(changeLogList);
            issueRepository.save(issue);

            ChangeLog changeLog = new ChangeLog();
            changeLog.setChangedTime(LocalDateTime.now());
            changeLog.setCurrentState(issueRequest.getChangeLog().getCurrentState());
            changeLog.setPreviousState(issueRequest.getChangeLog().getPreviousState());
            changeLog.setIssue(issue);
            changeLogList.add(changeLog);
            changeLogRepository.save(changeLog);
            //changeLog.setIssue(issue);


        }
        return commonResponse.buildResponse(HttpStatus.OK.value(), CommonMessage.UPDATED.value(),
                CommonMessage.UPDATED.message());
    }

    @Override
    public ResponseEntity<ApiResponse> getIssuesByProject(Integer projectId) {

        List<Issue> issuesList = issueRepository.findAllIssuesByProject(projectId);

        return commonResponse
                .buildResponse(HttpStatus.OK.value(), CommonMessage.SUCCESS.value(), CommonMessage.SUCCESS.message(),
                        issuesList);
    }

    @Override
    public ResponseEntity<ApiResponse> getAll() {

        List<Issue> issuesList = issueRepository.findAllByOrderByCreatedDateAsc();

        if (issuesList == null || issuesList.isEmpty()) {
            throw new DataNotFoundException("Issue details are empty");
        }
        return commonResponse
                .buildResponse(HttpStatus.OK.value(), CommonMessage.SUCCESS.value(), CommonMessage.SUCCESS.message(),
                        issuesList);
    }

    @Override
    public ResponseEntity<ApiResponse> getIssue(Integer id) {
        Optional<Issue> issue = issueRepository.findById(id);
        return commonResponse
                .buildResponse(HttpStatus.OK.value(), CommonMessage.SUCCESS.value(), CommonMessage.SUCCESS.message(),
                        issue);
    }



}
