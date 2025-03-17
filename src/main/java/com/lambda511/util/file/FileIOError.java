package com.lambda511.util.file;

/**
 * Created by blitzer on 05.05.2016.
 */
public class FileIOError extends RuntimeException {

    public FileIOError(String message) {
        super(message);
    }

    public FileIOError(String message, Throwable cause) {
        super(message, cause);
    }

}
