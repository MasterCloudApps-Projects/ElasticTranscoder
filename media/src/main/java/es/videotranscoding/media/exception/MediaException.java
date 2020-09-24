package es.videotranscoding.media.exception;

import java.io.IOException;

public class MediaException extends Exception {

    private static final long serialVersionUID = 1L;

    public MediaException(String message) {
        super(message);
    }

    public MediaException(String message, IOException e) {
        super(message, e);
    }

    public MediaException(String message, Exception e) {
        super(message, e);
    }

}