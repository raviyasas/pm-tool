package com.app.service;

import com.app.exception.DataNotFoundException;
import com.app.model.AppUser;
import com.app.model.Project;
import com.app.model.common.ApiResponse;
import com.app.model.common.CommonResponse;
import com.app.model.request.ProjectRequest;
import com.app.repository.ProjectRepository;
import com.app.repository.UserRepository;
import com.app.util.CommonMessage;
import com.app.util.Projectvalidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommonResponse commonResponse;

    @Override
    public ResponseEntity<ApiResponse> saveProject(ProjectRequest projectRequest) {

        // validate project request
        Projectvalidator.validateProject(projectRequest);

        if(userRepository.findByUsername(projectRequest.getCreatedUser()) == null){
            throw new DataNotFoundException("Username not found");
        }

        Project project = new Project();
        project.setProjectName(projectRequest.getProjectName());
        project.setDescription(projectRequest.getDescription());
        project.setCreatedTime(LocalDateTime.now());
        project.setCreatedUser(projectRequest.getCreatedUser());

        projectRepository.save(project);
        logger.debug("New project has been added - data: {}", project);
        return commonResponse.buildResponse(HttpStatus.CREATED.value(), CommonMessage.SUCCESS.value(),
                CommonMessage.SUCCESS.message());
    }

    @Override
    public ResponseEntity<ApiResponse> getAll() {
        List<Project> projectList = projectRepository.findAll();

        if(projectList == null || projectList.isEmpty()){
            throw new DataNotFoundException("Project details are empty");
        }

        logger.debug("Receiving all project details - data: {}", projectList);
        return commonResponse
                .buildResponse(HttpStatus.OK.value(), CommonMessage.SUCCESS.value(), CommonMessage.SUCCESS.message(),
                        projectList);
    }

    @Override
    public ResponseEntity<ApiResponse> getProject(Integer id) {

        Optional<Project> project = projectRepository.findById(id);

        if(project == null || project.isEmpty()){
            throw new DataNotFoundException("Project details are empty");
        }

        logger.debug("Retrieving project data for projectId: {} - data: {}", id, project);
        return commonResponse
                .buildResponse(HttpStatus.OK.value(), CommonMessage.SUCCESS.value(), CommonMessage.SUCCESS.message(),
                        project);
    }
}
