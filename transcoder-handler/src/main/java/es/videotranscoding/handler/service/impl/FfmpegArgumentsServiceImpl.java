package es.videotranscoding.handler.service.impl;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.videotranscoding.handler.dto.FFmpegArgumentsDTO;
import es.videotranscoding.handler.util.FfmpegArguments.AudioCodec;
import es.videotranscoding.handler.util.FfmpegArguments.AudioContainer;
import es.videotranscoding.handler.util.FfmpegArguments.Preset;
import es.videotranscoding.handler.util.FfmpegArguments.Resolution;
import es.videotranscoding.handler.util.FfmpegArguments.VideoCodec;
import es.videotranscoding.handler.util.FfmpegArguments.VideoContainer;

@Service
public class FfmpegArgumentsServiceImpl  {
    private final String AUDIO = "audio";
    private final String VIDEO = "video";

    public FFmpegArgumentsDTO getTranscodes(String type) {
        switch (type) {
            case AUDIO:
                return getAudioArguments();
            case VIDEO:
                return getVideoArguments();
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not type audio or video selected");
        }
    }

    private FFmpegArgumentsDTO getAudioArguments() {
        FFmpegArgumentsDTO fFmpegArgumentsDTO = new FFmpegArgumentsDTO();
        fFmpegArgumentsDTO.setCodecsAudio(Arrays.asList(AudioCodec.values()));
        fFmpegArgumentsDTO.setAudioContainers(Arrays.asList(AudioContainer.values()));
        return fFmpegArgumentsDTO;
    }

    private FFmpegArgumentsDTO getVideoArguments() {
        FFmpegArgumentsDTO fFmpegArgumentsDTO = new FFmpegArgumentsDTO();
        fFmpegArgumentsDTO.setCodecsVideo(Arrays.asList(VideoCodec.values()));
        fFmpegArgumentsDTO.setVideoContainers(Arrays.asList(VideoContainer.values()));
        fFmpegArgumentsDTO.setPresets(Arrays.asList(Preset.values()));
        fFmpegArgumentsDTO.setCodecsAudio(Arrays.asList(AudioCodec.values()));
        fFmpegArgumentsDTO.setResolutions(Arrays.asList(Resolution.values()));
        return fFmpegArgumentsDTO;
    }

}