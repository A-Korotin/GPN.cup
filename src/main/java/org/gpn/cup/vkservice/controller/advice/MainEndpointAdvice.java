package org.gpn.cup.vkservice.controller.advice;

import org.gpn.cup.vkservice.domain.vkApi.VkAPIError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MainEndpointAdvice extends ResponseEntityExceptionHandler {

    private static final int INVALID_ACCESS_TOKEN_ERROR_CODE = 5;
    private static final int INVALID_REQUEST_BODY_ERROR_CODE = 6;


    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(
            ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        VkAPIError error = new VkAPIError();
        error.setErrorCode(INVALID_ACCESS_TOKEN_ERROR_CODE);
        error.setErrorMessage("'vk_service_token' header was not found");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        VkAPIError error = new VkAPIError();
        error.setErrorCode(INVALID_REQUEST_BODY_ERROR_CODE);
        error.setErrorMessage("Invalid request body: mandatory parameters 'user_id' and/or 'group_id' are missing");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
