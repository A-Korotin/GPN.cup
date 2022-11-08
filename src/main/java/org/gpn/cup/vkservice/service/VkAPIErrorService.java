package org.gpn.cup.vkservice.service;

import org.gpn.cup.vkservice.domain.vkApi.VkAPIError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VkAPIErrorService {

    private static final int NOT_FOUND_ERROR_CODE = 100;
    private static final int INVALID_TOKEN_ERROR_CODE = 5;
    private static final int REQUEST_TIMEOUT_ERROR_CODE = 0;

    public ResponseEntity<?> determineResponseEntity(VkAPIError error) {
        return switch (error.getErrorCode()){
            case NOT_FOUND_ERROR_CODE -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            case INVALID_TOKEN_ERROR_CODE -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            case REQUEST_TIMEOUT_ERROR_CODE -> ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(error);
            default -> null; // todo plug
        };
    }
}
