package es.videotranscoding.transcoder.status.exception;

public class TranscoderStatusException extends Exception {

    private static final long serialVersionUID = 427175408916992531L;

    public TranscoderStatusException(String message) {
        super(message);
    }

    public TranscoderStatusException(String message, Exception e) {
        super(message, e);
    }

}
