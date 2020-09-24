package es.videotranscoding.handler.exception;

public class HandlerException extends Exception {

    private static final long serialVersionUID = 1L;

    public HandlerException(String message) {
        super(message);
    }

    public HandlerException(String message, Exception e) {
        super(message, e);
    }

}