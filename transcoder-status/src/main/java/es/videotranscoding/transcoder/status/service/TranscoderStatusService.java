package es.videotranscoding.transcoder.status.service;

import es.videotranscoding.transcoder.status.dto.TranscodeMediaDTO;
import es.videotranscoding.transcoder.status.exception.TranscoderStatusException;

public interface TranscoderStatusService {

    void update(TranscodeMediaDTO transcodeMediaDTO) throws TranscoderStatusException;

}
