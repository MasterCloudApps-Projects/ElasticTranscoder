package es.videotranscoding.media.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlatMediaDTO {

    private String id;

    private String type;

    private String name;

    private String path;

    private String user;

    private String filesize;

    private Boolean active;

    private String bitrate;

    private String container;

    private String audioCodec;

    private String resolution;

    private String videoCodec;

    private String duration;;

}