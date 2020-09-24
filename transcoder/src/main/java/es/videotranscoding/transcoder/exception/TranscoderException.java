package es.videotranscoding.transcoder.exception;

public class TranscoderException extends Exception {

    private static final long serialVersionUID = 1L;

    public TranscoderException(String message) {
        super(message);
    }

    public TranscoderException(String message, Exception e) {
        super(message, e);
    }

}