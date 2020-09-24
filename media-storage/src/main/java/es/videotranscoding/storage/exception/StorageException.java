package es.videotranscoding.storage.exception;

import java.io.IOException;

public class StorageException extends Exception {

    private static final long serialVersionUID = 1L;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, IOException e) {
        super(message, e);
    }

    public StorageException(String message, Exception e) {
        super(message, e);
    }

}