package com.lambda511.api.v1.controllers;

import com.lambda511.api.v1.response.ErrorMessage;
import com.lambda511.api.v1.response.Response;
import com.lambda511.api.v1.response.ResponseFactory;
import com.lambda511.util.file.FileIOError;
import com.lambda511.util.file.FileNotFoundError;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Created by blitzer on 23.04.2016.
 */

@ControllerAdvice
public class ExceptionControllerAdvice {

    @Autowired
    private ResponseFactory responseFactory;

    private Logger logger = Logger.getLogger(ExceptionControllerAdvice.class);


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> exceptionHandler(Exception ex) {
        logger.error("Error: ", ex);
        ErrorMessage errorMessage = new ErrorMessage(ErrorMessage.ErrorType.UNKNOWN,
                "An internal error has occurred! If the error persists, please contact the administrator");

        return new ResponseEntity<>(responseFactory.createErrorResponse(errorMessage), HttpStatus.OK);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> messageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        logger.error("Error: ", ex);
        ErrorMessage errorMessage = new ErrorMessage(ErrorMessage.ErrorType.INPUT_PARAMS,
                "Input message could not be read!");

        return new ResponseEntity<>(responseFactory.createErrorResponse(errorMessage), HttpStatus.OK);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<Response> typeNotAcceptableExceptionHandler(HttpMediaTypeNotAcceptableException ex) {
        logger.error("Error: ", ex);
        ErrorMessage errorMessage = new ErrorMessage(ErrorMessage.ErrorType.INPUT_PARAMS,
                "Could not find acceptable representation for request. Check API version (accept header)");

        return new ResponseEntity<>(responseFactory.createErrorResponse(errorMessage), HttpStatus.OK);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Response> requestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ErrorMessage.ErrorType.REQUEST_TYPE,
                String.format("Request method not supported: [%s]. Supported: [%s]",
                        ex.getMethod(), StringUtils.join(ex.getSupportedMethods(), ",")));

        return new ResponseEntity<>(responseFactory.createErrorResponse(errorMessage), HttpStatus.OK);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Response> missingParameterExceptionHandler(MissingServletRequestParameterException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ErrorMessage.ErrorType.INPUT_PARAMS,
                String.format("Required parameter missing! Name: [%s], Type: [%s]",
                        ex.getParameterName(), ex.getParameterType()));

        return new ResponseEntity<>(responseFactory.createErrorResponse(errorMessage), HttpStatus.OK);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<Response> typeMismatchExceptionHandler(TypeMismatchException ex) {
        logger.error("Error: ", ex);
        ErrorMessage errorMessage = new ErrorMessage(ErrorMessage.ErrorType.INPUT_PARAMS,
                String.format("Invalid input parameter: [%s], Required Type: [%s]",
                        ex.getValue(), ex.getRequiredType().getSimpleName()));

        return new ResponseEntity<>(responseFactory.createErrorResponse(errorMessage), HttpStatus.OK);
    }

    @ExceptionHandler(FileNotFoundError.class)
    public ResponseEntity<Response> fileFileNotFoundErrorHandler(FileNotFoundError ex) {
        logger.error("Error: ", ex);
        ErrorMessage errorMessage = new ErrorMessage(ErrorMessage.ErrorType.FILE_IO, "The requested file was not found!");
        return new ResponseEntity<>(responseFactory.createErrorResponse(errorMessage), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileIOError.class)
    public ResponseEntity<Response> fileWriteErrorHandler(FileIOError ex) {
        logger.error("Error: ", ex);
        ErrorMessage errorMessage = new ErrorMessage(ErrorMessage.ErrorType.FILE_IO, "An I/O error occurred while handling a file!");
        return new ResponseEntity<>(responseFactory.createErrorResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
