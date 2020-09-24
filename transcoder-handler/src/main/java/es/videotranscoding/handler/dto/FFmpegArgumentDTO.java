package es.videotranscoding.handler.dto;

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
public class FFmpegArgumentDTO {
    private VideoCodec videoCodec;

    private AudioCodec audioCodec;

    private Preset preset;

    private Resolution resolution;

    private VideoContainer videoContainer;

    private AudioContainer audioContainer;

}


