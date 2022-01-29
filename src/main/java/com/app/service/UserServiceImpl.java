package com.app.service;

import com.app.controller.IssueController;
import com.app.exception.BadRequestException;
import com.app.exception.DataNotFoundException;
import com.app.exception.EmailException;
import com.app.model.AppUser;
import com.app.model.Issue;
import com.app.model.common.ApiResponse;
import com.app.model.common.CommonResponse;
import com.app.model.reponse.UsernameIdResponse;
import com.app.model.request.AssignIssueRequest;
import com.app.model.request.UserRequest;
import com.app.repository.IssueRepository;
import com.app.repository.UserRepository;
import com.app.util.CommonMessage;
import com.app.util.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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

        // validate user request
        UserValidator.validateRequest(userRequest);

        // check the user is exists or not
        if (userRepository.findByUsername(userRequest.getUsername()) != null){
            throw new BadRequestException("Username already exists");
        }

        // save new user
        AppUser user = new AppUser();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        userRepository.save(user);
        logger.debug("New user has been created - data: {}", user);

        try{
            // send a mail to the user
            //mailService.sendEmail(userRequest.getEmail(), "Your account has been added","test message");
        }catch (Exception e){
            logger.error("An error has occurred while sending mail");
            throw new EmailException("An error has occurred while sending mail");
        }

        return commonResponse.buildResponse(HttpStatus.CREATED.value(), CommonMessage.SUCCESS.value(),
                CommonMessage.SUCCESS.message());
    }

    @Override
    public ResponseEntity<ApiResponse> getAll() {
        List<AppUser> userList = userRepository.findAll();
        logger.debug("Retrieving all users - data: {}", userList);

        return commonResponse
                .buildResponse(HttpStatus.OK.value(), CommonMessage.SUCCESS.value(), CommonMessage.SUCCESS.message(),
                        userList);
    }

    @Override
    public ResponseEntity<ApiResponse> getAllUsernames() {

        List<String> userNameList = userRepository.findAllUsernames();
        return commonResponse
                .buildResponse(HttpStatus.OK.value(), CommonMessage.SUCCESS.value(), CommonMessage.SUCCESS.message(),
                        userNameList);
    }


    @Override
    public ResponseEntity<ApiResponse> assignIssue(AssignIssueRequest assignIssueRequest) {
        AppUser user;
        Issue issue;

        if(assignIssueRequest == null || assignIssueRequest.getIssueId() == null || assignIssueRequest.getUserId() == null){
            throw new BadRequestException("Request data is null or empty");
        }

        try {
             user = userRepository.findById(assignIssueRequest.getUserId()).get();
             issue = issueRepository.findById(assignIssueRequest.getIssueId()).get();
        }catch (Exception e){
            logger.error("User or Issue data cannot be found");
            throw new DataNotFoundException("User or Issue data cannot be found");
        }

        List<Issue> issueList = user.getIssues();
        issueList.add(issue);

        user.setIssues(issueList);
        userRepository.save(user);
        logger.debug("The issue with issueId:{} has been assigned to userId: {}", assignIssueRequest.getIssueId(), assignIssueRequest.getUserId());

        return commonResponse.buildResponse(HttpStatus.OK.value(), CommonMessage.UPDATED.value(),
                CommonMessage.UPDATED.message());
    }

}
