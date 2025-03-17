package com.lambda511.util.file;

/**
 * Created by blitzer on 05.05.2016.
 */
public class FileNotFoundError extends FileIOError {

    public FileNotFoundError(String message) {
        super(message);
    }

    public FileNotFoundError(String message, Throwable cause) {
        super(message, cause);
    }
}
