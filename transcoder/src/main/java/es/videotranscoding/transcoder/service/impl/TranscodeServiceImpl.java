package es.videotranscoding.transcoder.service.impl;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import es.videotranscoding.transcoder.dto.TranscodeMediaDTO;
import es.videotranscoding.transcoder.exception.TranscoderException;
import es.videotranscoding.transcoder.exception.TranscoderRuntimeException;
import es.videotranscoding.transcoder.service.TranscodeService;
import es.videotranscoding.transcoder.utils.StreamGobbler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TranscodeServiceImpl implements TranscodeService {

    private final String PROGRESS_VIDEO_PATTERN = "(?<=time=)[\\d:.]*";
    private final String DURATION_VIDEO_PATTERN = "(?<=Duration: )[^,]*";
    private Pattern progreesVideoPattern;
    private Pattern durationVideoPattern;
    private double finalTime;

    private final ProducerService producerService;

    private final ExecutorService executorService;

    @PostConstruct
    public void init() {
        progreesVideoPattern = Pattern.compile(PROGRESS_VIDEO_PATTERN);
        durationVideoPattern = Pattern.compile(DURATION_VIDEO_PATTERN);
    }

    @Override
    public int transcode(TranscodeMediaDTO transcodeMediaDTO) throws TranscoderException {
        log.info("Transcoding file {}", transcodeMediaDTO.toString());
        transcodeMediaDTO.setActive(true);
        producerService.sendStatus(transcodeMediaDTO);
        try {
            finalTime = 0;
            Process process = new ProcessBuilder("bash", "-c", transcodeMediaDTO.getCommand()).redirectErrorStream(true)
                    .start();
            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), line -> {
                log.debug("FFMPEG commandline: {}", line);
                Matcher progressMatcher = progreesVideoPattern.matcher(line);
                Matcher durationVideoMatcher = durationVideoPattern.matcher(line);
                if (progressMatcher.find()) {
                    double diference = getDifference(finalTime, progressMatcher.group(0));
                     transcodeMediaDTO.setProcessed(diference);
                    if ((Math.round(transcodeMediaDTO.getProcessed() * 100.0) / 100.0) != 100) {
                        try {
                            producerService.sendStatus(transcodeMediaDTO);
                        } catch (TranscoderException e) {
                            log.error("Runtime transcoderException", e);
                            throw new TranscoderRuntimeException("Runtime transcoderException", e);
                        }
                    }
                }
                if (durationVideoMatcher.find()) {
                    log.debug("durationVideoMatcher found: {}", durationVideoMatcher.group(0));
                    finalTime = getDuration(durationVideoMatcher.group(0));
                }
            });
            executorService.submit(streamGobbler);
            int status = process.waitFor();
            if (status != 0) {
                transcodeMediaDTO.setProcessed(0);
                transcodeMediaDTO.setError(true);
            } else {
                transcodeMediaDTO.setProcessed(100);
            }
            transcodeMediaDTO.setActive(false);
            producerService.sendStatus(transcodeMediaDTO);
            log.info("File {} transcode with status {}", transcodeMediaDTO.toString(), status);
            return status;
        } catch (InterruptedException | IOException e) {
            log.error("Exception on command line transcode", e);
            transcodeMediaDTO.setProcessed(0);
            transcodeMediaDTO.setError(true);
            transcodeMediaDTO.setActive(false);
            producerService.sendStatus(transcodeMediaDTO);
            Thread.currentThread().interrupt();
            throw new TranscoderException("Exception on command line transcode", e);
        }
    }

    /**
     * Return the time that fault of the video to finish them.
     * 
     * @param finalTime2
     * @param timeVariable
     * @return
     */
    private double getDifference(Double finalTime2, String timeVariable) {
        String matchSplit[] = timeVariable.split(":");
        try {
            return ((Integer.parseInt(matchSplit[0]) * 3600 + Integer.parseInt(matchSplit[1]) * 60
                    + Double.parseDouble(matchSplit[2])) / finalTime2) * 100;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * The duration of the video
     * 
     * @param group
     * @return
     */
    private double getDuration(String group) {
        String[] hms = group.split(":");
        double toReturn = 0;
        try {
            toReturn = Integer.parseInt(hms[0]) * 3600 + Integer.parseInt(hms[1]) * 60 + Double.parseDouble(hms[2]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("getDuration return: {}", toReturn);
        return toReturn;
    }
}
