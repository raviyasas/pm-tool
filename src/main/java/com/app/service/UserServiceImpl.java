package com.app.service;

import com.app.model.AppUser;
import com.app.model.Issue;
import com.app.model.common.ApiResponse;
import com.app.model.common.CommonResponse;
import com.app.model.request.AssignIssueRequest;
import com.app.model.request.UserRequest;
import com.app.repository.IssueRepository;
import com.app.repository.UserRepository;
import com.app.util.CommonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private CommonResponse commonResponse;

    @Autowired
    private MailService mailService;

    @Override

    public ResponseEntity<ApiResponse> saveUser(UserRequest userRequest) {

        AppUser user = new AppUser();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        userRepository.save(user);

        mailService.sendEmail(userRequest.getEmail(), "Your account has been added","test message");

        return commonResponse.buildResponse(HttpStatus.CREATED.value(), CommonMessage.SUCCESS.value(),
                CommonMessage.SUCCESS.message());
    }

    @Override
    public ResponseEntity<ApiResponse> getAll() {
        List<AppUser> userList = userRepository.findAll();

        return commonResponse
                .buildResponse(HttpStatus.OK.value(), CommonMessage.SUCCESS.value(), CommonMessage.SUCCESS.message(),
                        userList);
    }


    @Override
    public ResponseEntity<ApiResponse> assignIssue(AssignIssueRequest assignIssueRequest) {

        AppUser user = userRepository.findById(assignIssueRequest.getUserId()).get();
        Issue issue = issueRepository.findById(assignIssueRequest.getIssueId()).get();

        List<Issue> issueList = user.getIssues();
        issueList.add(issue);

        user.setIssues(issueList);
        userRepository.save(user);

        return commonResponse.buildResponse(HttpStatus.OK.value(), CommonMessage.UPDATED.value(),
                CommonMessage.UPDATED.message());
    }

}
