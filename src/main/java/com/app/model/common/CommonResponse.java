package com.app.model.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CommonResponse {

    ResponseEntity<ApiResponse> buildResponse(HttpHeaders httpHeader, int httpStatusCode, int apiStatusCode,
                                              String message, Object data, Map<String, Object> additionalParams);

    ResponseEntity<ApiResponse> buildResponse(int httpStatusCode, int apiStatusCode, String message);

    ResponseEntity<ApiResponse> buildResponse(int httpStatusCode, int apiStatusCode, String message, Object data);
}
