package es.videotranscoding.transcoder.service;

import es.videotranscoding.transcoder.dto.TranscodeMediaDTO;
import es.videotranscoding.transcoder.exception.TranscoderException;

public interface TranscodeService {

     int transcode(TranscodeMediaDTO transcodeMediaDTO) throws TranscoderException;

}