package es.videotranscoding.handler.util;

import java.util.regex.MatchResult;

import lombok.Data;

@Data
public class FfmpegStreamLine {

    private int numStream;
    private String language;
    private String type;
    private String codec;

    public FfmpegStreamLine(MatchResult matchResult) {
        this.numStream = Integer.parseInt(matchResult.group(1));
        this.language = matchResult.group(2);
        this.type = matchResult.group(3);
        this.codec = matchResult.group(4).toUpperCase();
    }
}