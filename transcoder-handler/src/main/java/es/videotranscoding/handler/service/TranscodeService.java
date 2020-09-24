package es.videotranscoding.handler.service;

import java.util.List;

import es.videotranscoding.handler.dto.FFmpegArgumentDTO;
import es.videotranscoding.handler.dto.FlatMediaDTO;
import es.videotranscoding.handler.dto.TranscodeMediaDTO;
import es.videotranscoding.handler.exception.HandlerException;

public interface TranscodeService {

    void createTranscodes(String uuid, List<FFmpegArgumentDTO> ffmpegArguments) throws HandlerException;

    void mediaInfo(FlatMediaDTO flatMedia) throws HandlerException;

    void mediaInfo(TranscodeMediaDTO transcodeMediaDTO) throws HandlerException;

}