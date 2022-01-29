package com.app.exception;

import com.app.model.common.ApiResponse;
import com.app.model.common.CommonResponse;
import com.app.util.CommonMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class MainExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(MainExceptionAdvice.class);
    private static final String ERROR="error : ";

    @Autowired
    private CommonResponse commonResponse;

    @ExceptionHandler(DataNotFoundException.class)
    public final ResponseEntity<ApiResponse> handleDataNotFoundException(DataNotFoundException ex) {
        logger.error(ERROR, ex);
        return commonResponse.buildResponse(HttpStatus.NOT_FOUND.value(), CommonMessage.NOT_FOUND.value(),
                ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ApiResponse> handleBadRequestException (BadRequestException ex) {
        return commonResponse.buildResponse(HttpStatus.BAD_REQUEST.value(), CommonMessage.BAD_REQUEST.value(),
                ex.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public final ResponseEntity<ApiResponse> handleInvalidRequestException (InvalidRequestException ex) {
        return commonResponse.buildResponse(HttpStatus.BAD_REQUEST.value(), CommonMessage.BAD_REQUEST.value(),
                ex.getMessage());
    }

    @ExceptionHandler(EmailException.class)
    public final ResponseEntity<ApiResponse> handleEmailException (EmailException ex) {
        return commonResponse.buildResponse(HttpStatus.BAD_GATEWAY.value(), CommonMessage.BAD_GATEWAY.value(),
                ex.getMessage());
    }
}
