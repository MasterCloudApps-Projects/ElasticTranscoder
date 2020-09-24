package es.videotranscoding.handler.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MediaInfoDTO {
    private String duration;

    private String bitrate;

    private String filesize;

    private String videoCodec;

    private String audioCodec;

    private String container;

    private String resolution;


}