package com.scooterjee.app.expostion.error;

import com.scooterjee.app.domain.user.exception.OperationNotPermittedException;
import com.scooterjee.app.domain.user.exception.UserAlreadyExistsException;
import com.scooterjee.kernel.exception.SimpleServiceException;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class ErrorHandler {

    private final static int HTTP_ERROR = 400;
    private final static int HTTP_CONFLICT = 409;

    private final static int HTTP_UNAUTHORIZED = 401;

    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<ErrorResponseDTO> manageSQLIntegrityConstraintViolationException(
        final SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException
    ) {
        ErrorResponseDTO error = new ErrorResponseDTO(sqlIntegrityConstraintViolationException.getMessage());
        return ResponseEntity.status(HTTP_ERROR).body(error);
    }

    @ExceptionHandler(value = {SimpleServiceException.class})
    public ResponseEntity<ErrorResponseDTO> manageSimpleServiceException(
        final SimpleServiceException simpleServiceException
    ) {
        ErrorResponseDTO error = new ErrorResponseDTO(simpleServiceException.getMessage());
        return ResponseEntity.status(HTTP_ERROR).body(error);
    }

    @ExceptionHandler(value = {SpelEvaluationException.class})
    public ResponseEntity<ErrorResponseDTO> manageSpelEvaluationException(
        final SpelEvaluationException spelEvaluationException
    ) {
        ErrorResponseDTO error = new ErrorResponseDTO(spelEvaluationException.getMessage());
        return ResponseEntity.status(HTTP_ERROR).body(error);
    }

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ResponseEntity<ErrorResponseDTO> manageUserAlreadyExistsException(
        final UserAlreadyExistsException userAlreadyExistsException
    ) {
        ErrorResponseDTO error = new ErrorResponseDTO(userAlreadyExistsException.getMessage());
        return ResponseEntity.status(HTTP_CONFLICT).body(error);
    }

    @ExceptionHandler(value = {OperationNotPermittedException.class})
    public ResponseEntity<ErrorResponseDTO> manageOperationNotPermittedException(
        final OperationNotPermittedException operationNotPermittedException
    ) {
        ErrorResponseDTO error = new ErrorResponseDTO(operationNotPermittedException.getMessage());
        return ResponseEntity.status(HTTP_UNAUTHORIZED).body(error);
    }
}
