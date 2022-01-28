package com.app.model.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommonResponseImpl implements CommonResponse {

    @Override
    public ResponseEntity<ApiResponse> buildResponse(HttpHeaders httpHeader, int httpStatusCode, int apiStatusCode,
                                                     String message, Object data, Map<String, Object> additionalParams) {
        return new ApiResponse.ApiResponseBuilder<>(httpStatusCode, apiStatusCode, message).withHttpHeader(httpHeader)
                .withData(data).withAdditionalParams(additionalParams).build();
    }

    @Override
    public ResponseEntity<ApiResponse> buildResponse(int httpStatusCode, int apiStatusCode, String message) {
        return new ApiResponse.ApiResponseBuilder<>(httpStatusCode, apiStatusCode, message).build();
    }

    @Override
    public ResponseEntity<ApiResponse> buildResponse(int httpStatusCode, int apiStatusCode, String message,
                                                     Object data) {
        return new ApiResponse.ApiResponseBuilder<>(httpStatusCode, apiStatusCode, message).withData(data).build();
    }
}
