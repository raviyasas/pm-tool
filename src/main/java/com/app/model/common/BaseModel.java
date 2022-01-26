package com.app.model.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
public class BaseModel implements Serializable {

    private static final long serialVersionId = 1L;
}
