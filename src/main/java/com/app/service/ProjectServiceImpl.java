package com.app.service;

import com.app.exception.DataNotFoundException;
import com.app.model.Project;
import com.app.model.common.ApiResponse;
import com.app.model.common.CommonResponse;
import com.app.model.request.ProjectRequest;
import com.app.repository.ProjectRepository;
import com.app.util.CommonMessage;
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

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CommonResponse commonResponse;

    @Override
    public ResponseEntity<ApiResponse> saveProject(ProjectRequest projectRequest) {

        if(projectRequest == null || projectRequest.getProjectName() == null){
            throw new DataNotFoundException("Project data required");
        }

        Project project = new Project();
        project.setProjectName(projectRequest.getProjectName());
        project.setDescription(projectRequest.getDescription());
        project.setCreatedTime(LocalDateTime.now());
        project.setCreatedUser(projectRequest.getCreatedUser());

        projectRepository.save(project);

        return commonResponse.buildResponse(HttpStatus.CREATED.value(), CommonMessage.SUCCESS.value(),
                CommonMessage.SUCCESS.message());
    }

    @Override
    public ResponseEntity<ApiResponse> getAll() {
        List<Project> projectList = projectRepository.findAll();

        if(projectList == null || projectList.isEmpty()){
            throw new DataNotFoundException("Project details are empty");
        }
        return commonResponse
                .buildResponse(HttpStatus.OK.value(), CommonMessage.SUCCESS.value(), CommonMessage.SUCCESS.message(),
                        projectList);
    }

    @Override
    public ResponseEntity<ApiResponse> getProject(Integer id) {

        Optional<Project> project = projectRepository.findById(id);
        return commonResponse
                .buildResponse(HttpStatus.OK.value(), CommonMessage.SUCCESS.value(), CommonMessage.SUCCESS.message(),
                        project);
    }
}
