package es.videotranscoding.handler.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.videotranscoding.handler.dto.FFmpegArgumentDTO;
import es.videotranscoding.handler.dto.TranscodeMediaDTO;
import es.videotranscoding.handler.model.FlatMedia;
import es.videotranscoding.handler.model.TranscodeMedia;
import es.videotranscoding.handler.service.VideoTranscodeService;
import es.videotranscoding.handler.util.FfmpegArguments;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VideoTranscodeServiceImpl implements VideoTranscodeService {

        @Autowired
        private TranscodeMediaServiceImpl transcodeMediaServiceImpl;

        @Autowired
        private ProducerService producerService;

        @Autowired
        private ModelMapper modelMapper;

        @Value("${path.media.transcode}")
        private Path transcodeMediaFolder;

        private final String UNDERSCORE = "_";

        @Override
        public void createTranscodes(FlatMedia flatMedia, List<FFmpegArgumentDTO> fFmpegArgumentsDTOs) {
                fFmpegArgumentsDTOs.forEach(ffmpegArgument -> createTranscode(flatMedia, ffmpegArgument));
        }

        private void createTranscode(FlatMedia flatMedia, FFmpegArgumentDTO ffmpegArgument) {
                Path transcodePath = createFolderForTranscode(
                                Path.of(flatMedia.getPath()).getParent().getFileName().toString(),
                                ffmpegArgument.getVideoCodec().name(), ffmpegArgument.getResolution().name(),
                                ffmpegArgument.getAudioCodec().name(), ffmpegArgument.getPreset().name(),
                                ffmpegArgument.getVideoContainer().name());
                String finalPathFile = getFinalPathFile(flatMedia.getPath(), transcodePath.toString(), ffmpegArgument);
                TranscodeMedia transcodeMedia = new TranscodeMedia();
                transcodeMedia.setActive(false);
                transcodeMedia.setName(flatMedia.getName());
                transcodeMedia.setAudioCodec(ffmpegArgument.getAudioCodec().name());
                transcodeMedia.setVideoCodec(ffmpegArgument.getVideoCodec().name());
                transcodeMedia.setPath(finalPathFile);
                log.debug("Final path of transcodeMedia {}", transcodeMedia.getPath());
                transcodeMedia.setFlatMediaId(flatMedia.getId());
                transcodeMedia.setContainer(ffmpegArgument.getVideoContainer().name());
                transcodeMedia.setUser(SecurityContextHolder.getContext().getAuthentication().getName());
                transcodeMedia.setType(flatMedia.getType());
                transcodeMedia.setResolution(ffmpegArgument.getResolution().name());
                transcodeMedia.setPreset(ffmpegArgument.getPreset().name());
                transcodeMedia.setCommand(
                                createFffmpegTranscodeCommand(finalPathFile, flatMedia.getPath(), ffmpegArgument));
                transcodeMediaServiceImpl.save(transcodeMedia);
                producerService.sendTranscode(modelMapper.map(transcodeMedia, TranscodeMediaDTO.class));
        }

        private String getFinalPathFile(String origin, String output, FFmpegArgumentDTO arguments) {
                return Path.of(output, FilenameUtils.getBaseName(origin)).toString() + "."
                                + arguments.getVideoContainer().toString();
        }

        private String createFffmpegTranscodeCommand(String finalPathFile, String origin, FFmpegArgumentDTO arguments) {
                // TODO: THREADS, AUDIO BITRATE AND CRF "-threads 1", VP9 NEED FIX.
                if (arguments.getVideoCodec().name().equals("VP9")) {
                        return String.join(" ",
                                        Arrays.asList("ffmpeg -y -i", origin, arguments.getVideoCodec().toString(),
                                                        arguments.getResolution().toString(), "-crf " + 15,
                                                        FfmpegArguments.AudioCodec.LIVOPUS.toString(), "-b:a " + "320k",
                                                        finalPathFile));
                } else {
                        return String.join(" ", Arrays.asList("ffmpeg -y -i", origin, arguments.getVideoCodec().toString(),
                                        arguments.getResolution().toString(), "-crf " + 15,
                                        arguments.getPreset().toString(), arguments.getAudioCodec().toString(),
                                        "-b:a " + "320k", finalPathFile));
                }
        }

        private Path createFolderForTranscode(String path, String... arguments) {
                Path finalPath = Path.of(transcodeMediaFolder.toString(), path, String.join(UNDERSCORE, arguments));
                if (!Files.exists(finalPath)) {
                        try {
                                Files.createDirectories(finalPath);
                        } catch (IOException e) {
                                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                                "Cannot create directory on " + finalPath.getParent().toString(), e);
                        }
                }
                return finalPath;
        }
}
