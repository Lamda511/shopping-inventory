package com.lambda511.api.v1.response;

/**
 * Created by blitzer on 23.04.2016.
 */
public class ErrorMessage {

    public enum ErrorType {
        INPUT_PARAMS,
        REQUEST_TYPE,
        RESOURCE_NOT_FOUND,
        FILE_IO,
        UNKNOWN
    }

    private ErrorType errorType;
    private String message;

    public ErrorMessage(ErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public String getMessage() {
        return message;
    }
}
