package com.app.model.request;

import com.app.model.common.BaseModel;
import lombok.Data;

@Data
public class ProjectRequest extends BaseModel {

    private String projectName;
    private String description;
    private String createdUser;
}
