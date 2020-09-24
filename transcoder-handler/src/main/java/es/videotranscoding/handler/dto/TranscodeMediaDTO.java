package es.videotranscoding.handler.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TranscodeMediaDTO {

    private String id;

    private String flatMediaId;

    private String type;

    private String name;

    private String path;

    private String audioCodec;

    private String videoCodec;

    private String container;

    private String preset;

    private String resolution;

    private double processed;

    private String command;

    private Boolean active;

    private Boolean error;

    private String user;

    private String filesize;

    private String bitrate;
}
