package edu.daidp.shoppingwebapp.common.exception;


import edu.daidp.shoppingwebapp.common.constant.COMMON_CONSTANT;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
// Globally handle exceptions for RESTful controllers.
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    // mark specific exception thrown by controller will be handler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    // config status of the response
    public ApplicationResponse<Exception> handleAllException(Exception ex, WebRequest request) {
        return new ApplicationResponse<Exception>(COMMON_CONSTANT.APP_STATUS.DEFAULT_EXCEPTION.CODE,
                                                  ex.getLocalizedMessage());
    }

    @ExceptionHandler(NoContentFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApplicationResponse<Exception> noContentFoundException(Exception ex, WebRequest request) {
        return new ApplicationResponse<Exception>(COMMON_CONSTANT.APP_STATUS.NO_DATA_FOUND.CODE,
                                                  COMMON_CONSTANT.APP_STATUS.NO_DATA_FOUND.MESSAGE,
                                                  ex,
                                                  Arrays.asList());
    }


    @ExceptionHandler(DuplicateDataException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApplicationResponse<String> duplicateDataException(Exception ex, WebRequest request) {
        return new ApplicationResponse<String>(COMMON_CONSTANT.APP_STATUS.DUPLICATE_DATA.CODE,
                                               COMMON_CONSTANT.APP_STATUS.DUPLICATE_DATA.MESSAGE,
                                               ex.getMessage(),
                                               Arrays.stream(ex.getStackTrace())
                                                       .map(stackTraceElement -> stackTraceElement.toString()).collect(
                                                               Collectors.toList()));
    }


    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApplicationResponse<String> authenticationException(Exception ex, WebRequest request) {
        return new ApplicationResponse<String>(COMMON_CONSTANT.APP_STATUS.AUTHENTICATION_ERROR.CODE,
                                               COMMON_CONSTANT.APP_STATUS.AUTHENTICATION_ERROR.MESSAGE,
                                               ex.getMessage(),
                                               Arrays.stream(ex.getStackTrace())
                                                       .map(stackTraceElement -> stackTraceElement.toString()).collect(
                                                               Collectors.toList()));
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ApplicationResponse<Object> error = new ApplicationResponse<Object>(
                COMMON_CONSTANT.APP_STATUS.VALIDATE_EXCEPTION.CODE,
                COMMON_CONSTANT.APP_STATUS.VALIDATE_EXCEPTION.MESSAGE,
                null,
                details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}