package es.videotranscoding.transcoder.status.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TranscodeMediaStatusDTO {

    private String id;

    private String flatMediaId;

    private String type;

    private String name;

    private String audioCodec;

    private String videoCodec;

    private String container;

    private String preset;

    private String resolution;

    private double processed;

    private String user;

    private String bitrate;

}
