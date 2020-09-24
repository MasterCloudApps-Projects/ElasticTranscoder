package es.videotranscoding.handler.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FfmpegArguments {

    private VideoCodec videoCodec;
    private Resolution resolution;
    private int crf; // 1-51 (-crf NUMBER) 1 best, 51 worst
    private Preset preset;
    private int threads;// 1-X (-threads NUMBER)
    private AudioCodec audioCodec;
    private int bitrateAUdio; // (-b:a NUMBERk)
    private AudioContainer audioContainer;
    private VideoContainer videoContainer;


    public enum VideoCodec {

        HEVC(" -codec:v libx265 "), VP9(" -codec:v libvpx-vp9 "), H264(" -codec:v libx264 ");

        private final String videoCodec;

        VideoCodec(String x) {
            this.videoCodec = x;
        }

        @Override
        public String toString() {
            return videoCodec;
        }
    }

    public enum Resolution {

        UHD(" -vf scale=3840:-2 "), QUAD_HD(" -vf scale=2560:-2 "), FULL_HD(" -vf scale=1920:-2 "),
        HD(" -vf scale=1280:-2 "), qHD(" -vf scale=960:-2 "), nHD(" -vf scale=640:-2 ");

        private final String resolution;

        Resolution(String resolution) {
            this.resolution = resolution;
        }

        @Override
        public String toString() {
            return resolution;
        }
    }

    public enum Preset {
        ULTRAFAST(" -preset ultrafast "), SUPERFAST(" -preset superfast "), VERYFAST(" -preset veryfast "),
        FASTER(" -preset faster "), FAST(" -preset  fast "), MEDIUM(" -preset  medium "), SLOW(" -preset slow "),
        SLOWER(" -preset slower "), VERYSLOW(" -preset  veryslow ");

        private final String preset;

        Preset(String preset) {
            this.preset = preset;
        }

        @Override
        public String toString() {
            return preset;
        }
    }

    public enum AudioCodec {

        AAC(" -codec:a aac "), LIVOPUS(" -codec:a libopus "),
        COPY(" -codec:a copy ");

        private final String audioCodec;

        AudioCodec(String x) {
            this.audioCodec = x;
        }

        @Override
        public String toString() {
            return audioCodec;
        }
    }

    public enum VideoContainer {

        MP4("mp4"), MKV("mkv"), WEBM("webm"), AVI("avi"), FLV("flv"), MOV("mov");

        private final String container;

        VideoContainer(String container) {
            this.container = container;
        }

        @Override
        public String toString() {
            return container;
        }
    }


    public enum AudioContainer {

         AAC("aac"), MP3("mp3"),OGG("ogg");

        private final String container;

        AudioContainer(String container) {
            this.container = container;
        }

        @Override
        public String toString() {
            return container;
        }
    }

}