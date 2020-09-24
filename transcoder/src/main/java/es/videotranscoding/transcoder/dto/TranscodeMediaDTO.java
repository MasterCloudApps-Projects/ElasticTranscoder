package es.videotranscoding.transcoder.dto;

import java.io.Serializable;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TranscodeMediaDTO implements Serializable{

    private static final long serialVersionUID = -8087084651782576313L;

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