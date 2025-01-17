package faang.school.projectservice.controller;

import com.amazonaws.services.kms.model.NotFoundException;
import com.atlassian.jira.rest.client.api.RestClientException;
import faang.school.projectservice.exception.DataValidationException;
import faang.school.projectservice.exception.DeniedInAccessException;
import faang.school.projectservice.exception.IllegalEntityException;
import faang.school.projectservice.exception.IllegalSubProjectsStatusException;
import faang.school.projectservice.exception.errorResponse.ErrorResponse;
import faang.school.projectservice.exception.errorResponse.JiraErrorResponse;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice(basePackages = "faang.school.projectservice")
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerNotFoundException(NotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(DeniedInAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleDeniedInAccessException(DeniedInAccessException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFeignException(FeignException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(IllegalSubProjectsStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalSubProjectStatusException(IllegalSubProjectsStatusException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorResponse handlerIllegalStateException(IllegalStateException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(DataValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerDataValidationException(DataValidationException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(RestClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JiraErrorResponse handlerRestClientException(RestClientException exception) {
        List<String> messages = new ArrayList<>();
        Map<String, String> errors = new HashMap<>();

        exception.getErrorCollections().forEach(errorCollection -> {
            messages.addAll(errorCollection.getErrorMessages());
            errors.putAll(errorCollection.getErrors());
        });

        return new JiraErrorResponse(messages, errors);
    }

    @ExceptionHandler(IllegalEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalEntityException(IllegalEntityException exception) {
        return new ErrorResponse(exception.getMessage());
    }
}