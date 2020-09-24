package es.videotranscoding.storage.util;

public enum VideoExtensions {

    /**
     * Mp4 extension
     */
    MP4("mp4"),
    /**
     * Mkv extension
     */
    MKV("mkv"),
    /**
     * Webm extension
     */
    WEBM("webm"),
    /**
     * Avi extension
     */
    AVI("avi"),
    /**
     * Flv extension
     */
    FLV("flv"),
    /**
     * M4S extension
     */
    M4S("m4s"),
    /**
     * Mov extension
     */
    MOV("mov");

    final String extension;

    VideoExtensions(String x) {
        this.extension = x;
    }
}