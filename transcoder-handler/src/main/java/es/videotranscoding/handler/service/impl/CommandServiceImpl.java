package es.videotranscoding.handler.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import es.videotranscoding.handler.dto.MediaInfoDTO;
import es.videotranscoding.handler.exception.HandlerException;
import es.videotranscoding.handler.service.CommandService;
import es.videotranscoding.handler.util.FfmpegStreamLine;
import es.videotranscoding.handler.util.StreamGobbler;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommandServiceImpl implements CommandService {

    private final String DURATION_VIDEO_PATTERN = "(?<=Duration: )[^,]*";
    private final String BITRATE_PATTERN = "(?<=bitrate: )[^\n]*";
    private final String STREAM_PATTERN = "Stream #0:(\\d+)[(](\\w+?)[)]: (\\w+): (\\w+) [(*](\\w+?)[)*] ";
    private final String RESOLUTION_PATTERN = "\\), (\\d+x\\d+) \\[";
    private Pattern durationVideoPattern;
    private Pattern streamPattern;
    private Pattern resolutionPattern;

    @PostConstruct
    private void postConstruct() {
        durationVideoPattern = Pattern.compile(DURATION_VIDEO_PATTERN);
        streamPattern = Pattern.compile(STREAM_PATTERN);
        resolutionPattern = Pattern.compile(RESOLUTION_PATTERN);
    }

    @Override
    public MediaInfoDTO getMediaInfo(String path) throws HandlerException {
        MediaInfoDTO mediaInfo = new MediaInfoDTO();
        mediaInfo.setFilesize((getSizeMB(new File(path)) + " MB").replace(",", "."));
        mediaInfo.setContainer(getContainerName(path));
        String command = String.join(" ", "ffprobe", path);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command("bash", "-c", command);
            builder.redirectErrorStream(true);
            Process process = builder.start();
            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), line -> {
                log.info(line);
                Matcher durationVideoMatcher = durationVideoPattern.matcher(line);
                Matcher streamMatcher = streamPattern.matcher(line);
                Matcher resolutionMatcher = resolutionPattern.matcher(line);
                if (durationVideoMatcher.find()) {
                    log.info("Duration FOUND");
                    mediaInfo.setDuration(Double.toString(getDuration(durationVideoMatcher.group())));
                    mediaInfo.setBitrate(getBitrate(line));
                }
                if (streamMatcher.find()) {
                    FfmpegStreamLine streamLine = new FfmpegStreamLine(streamMatcher.toMatchResult());
                    if (streamLine.getType().equals("Video")) {
                        mediaInfo.setVideoCodec(streamLine.getCodec());
                    }
                    if (streamLine.getType().equals("Audio")) {
                        mediaInfo.setAudioCodec(streamLine.getCodec());
                    }
                }
                if (resolutionMatcher.find()) {
                    mediaInfo.setResolution(resolutionMatcher.group(1));
                }
            });
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new HandlerException("Media info not retrivied corrected for this file " + path);
            }
            return mediaInfo;
        } catch (IOException | InterruptedException e) {
            log.error("IOException or interrupt getMediaInfo ", e);
            Thread.currentThread().interrupt();
            throw new HandlerException("IOException or interruptException getMediaInfo ", e);
        }
    }

    private String getContainerName(String path) {
        return FilenameUtils.getExtension(path).toUpperCase();
    }

    private String getBitrate(String line) {
        Pattern pattern = Pattern.compile(BITRATE_PATTERN);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    /**
     * The duration of the video
     * 
     * @param group
     * @return
     */
    private double getDuration(String group) {
        String[] hms = group.split(":");
        return Integer.parseInt(hms[0]) * 3600 + Integer.parseInt(hms[1]) * 60 + Double.parseDouble(hms[2]);
    }

    private String getSizeMB(File f) {
        double fileSizeInBytes = f.length();
        double fileSizeInKB = fileSizeInBytes / 1024;
        double fileSizeInMB = fileSizeInKB / 1024;
        return String.format("%.2f", fileSizeInMB);
    }

}