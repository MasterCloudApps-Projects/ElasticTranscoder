package es.videotranscoding.handler.service;

import java.util.List;

import es.videotranscoding.handler.dto.FFmpegArgumentDTO;
import es.videotranscoding.handler.model.FlatMedia;

public interface VideoTranscodeService {

    void createTranscodes(FlatMedia flatMedia,
            List<FFmpegArgumentDTO> fFmpegArgumentsDTOs);

}