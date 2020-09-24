package es.videotranscoding.transcoder.exception;

public class TranscoderRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TranscoderRuntimeException(String message) {
        super(message);
    }

    public TranscoderRuntimeException(String message, Exception e) {
        super(message, e);
    }

}
