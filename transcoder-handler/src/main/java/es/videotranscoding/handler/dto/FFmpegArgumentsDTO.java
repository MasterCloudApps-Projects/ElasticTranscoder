package es.videotranscoding.handler.dto;

import java.util.List;

import es.videotranscoding.handler.util.FfmpegArguments.AudioCodec;
import es.videotranscoding.handler.util.FfmpegArguments.AudioContainer;

import es.videotranscoding.handler.util.FfmpegArguments.Preset;
import es.videotranscoding.handler.util.FfmpegArguments.Resolution;
import es.videotranscoding.handler.util.FfmpegArguments.VideoCodec;
import es.videotranscoding.handler.util.FfmpegArguments.VideoContainer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FFmpegArgumentsDTO {

    private List<VideoCodec> codecsVideo;

    private List<AudioCodec> codecsAudio;

    private List<Preset> presets;

    private List<Resolution> resolutions;

    private List<VideoContainer> videoContainers;

    private List<AudioContainer> audioContainers;

}