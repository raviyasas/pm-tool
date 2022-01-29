package com.app.service;

import com.app.exception.BadRequestException;
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
import com.app.util.IssueValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(IssueServiceImpl.class);

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

        // validate the issue request
        IssueValidator.validateRequest(issueRequest);

        // validate the projectId
        Optional<Project> project = projectRepository.findById(issueRequest.getProjectId());
        if (project.isEmpty()) {
            throw new DataNotFoundException("Project ID is null or not existing");
        }

        AppUser user = userRepository.findByUsername(issueRequest.getCreatedUser());

        if(user == null){
            throw new DataNotFoundException("Username not found");
        }

        List<AppUser> appUserList = new ArrayList<>();
        appUserList.add(user);

        // update the issue
        Issue issue = new Issue();
        issue.setCreatedUser(issueRequest.getCreatedUser());
        issue.setIssueState(issueRequest.getIssueState());
        issue.setIssueType(issueRequest.getIssueType());
        issue.setCreatedDate(LocalDateTime.now());
        issue.setIssueDescription(issueRequest.getIssueDescription());
        issue.setProjectId(project.get().getProjectId());
        issue.setProject(project.get());
        issue.setIsAssigned(false);
        issue.setUsers(appUserList);
        issue.setUserId(user.getUserId());

        issueRepository.save(issue);
        logger.debug("New issue has been created - data: {}", issue);

        return commonResponse.buildResponse(HttpStatus.CREATED.value(), CommonMessage.SUCCESS.value(),
                CommonMessage.SUCCESS.message());
    }

    @Override
    public ResponseEntity<ApiResponse> updateIssue(Integer id, IssueRequest issueRequest) {

        // validate issue request
        IssueValidator.validateRequest(issueRequest);

        // check the project is exists or not
        Optional<Project> project = projectRepository.findById(issueRequest.getProjectId());
        if (project.isEmpty()) {
            throw new DataNotFoundException("Project ID is null or not existing");
        }

        // get the existing issue
        Optional<Issue> existingIssue = issueRepository.findById(id);
        if (existingIssue.isPresent()) {

            // get the change log of the existing issue
            List<ChangeLog> changeLogList = existingIssue.get().getChangeLog();

            if(changeLogList == null || changeLogList.isEmpty()){
                // create a new change log if the change log is empty
                changeLogList = new ArrayList<>();
            }

            // check the user is exists or not
            AppUser user = userRepository.findById(issueRequest.getUserId()).get();

            if(user == null){
                throw new BadRequestException("User does not exist");
            }

            //get existing users of the issue
            List<AppUser> users = existingIssue.get().getUsers();
            if(!users.contains(user)){
                users.add(user);
            }

            // update the issue
            Issue issue = existingIssue.get();
            issue.setIssueState(issueRequest.getChangeLog().getCurrentState());
            issue.setIssueType(issueRequest.getIssueType());
            issue.setIssueDescription(issueRequest.getIssueDescription());
            issue.setUsers(users);
            issue.setIsAssigned(true);
            issue.setProject(project.get());
            issueRepository.save(issue);
            logger.debug("An issue has been updated. issueId: {} - data: {}", issueRequest.getIssueId(), issue);

            ChangeLog changeLog = new ChangeLog();
            changeLog.setChangedTime(LocalDateTime.now());
            changeLog.setCurrentState(issueRequest.getChangeLog().getCurrentState());
            changeLog.setPreviousState(issueRequest.getChangeLog().getPreviousState());
            changeLog.setIssue(issue);
            changeLogList.add(changeLog);
            changeLogRepository.save(changeLog);
            logger.debug("A change log has been updated. issueId: {} - data: {}", issueRequest.getIssueId(), changeLog);

            List<Issue> issueList = user.getIssues();
            issueList.add(issue);

            user.setIssues(issueList);
            userRepository.save(user);
        }
        return commonResponse.buildResponse(HttpStatus.OK.value(), CommonMessage.UPDATED.value(),
                CommonMessage.UPDATED.message());
    }

    @Override
    public ResponseEntity<ApiResponse> getIssuesByProject(Integer projectId) {

        List<Issue> issuesList = issueRepository.findAllIssuesByProject(projectId);
        logger.debug("Retrieving issue for projectId: {} - data: {}", projectId, issuesList);

        return commonResponse
                .buildResponse(HttpStatus.OK.value(), CommonMessage.SUCCESS.value(), CommonMessage.SUCCESS.message(),
                        issuesList);
    }

    @Override
    public ResponseEntity<ApiResponse> getAll() {

        List<Issue> issuesList = issueRepository.findAllByOrderByCreatedDateAsc();
        logger.debug("Retrieving all issues - data: {}", issuesList);

        if (issuesList == null || issuesList.isEmpty()) {
            throw new DataNotFoundException("Issue details are required");
        }
        return commonResponse
                .buildResponse(HttpStatus.OK.value(), CommonMessage.SUCCESS.value(), CommonMessage.SUCCESS.message(),
                        issuesList);
    }

    @Override
    public ResponseEntity<ApiResponse> getIssue(Integer id) {

        if(id == null){
            throw new BadRequestException("IssueId is required");
        }

        Optional<Issue> issue = issueRepository.findById(id);
        logger.debug("Retrieving issue for issueId: {} - data: {}", id, issue);

        return commonResponse
                .buildResponse(HttpStatus.OK.value(), CommonMessage.SUCCESS.value(), CommonMessage.SUCCESS.message(),
                        issue);
    }

}
