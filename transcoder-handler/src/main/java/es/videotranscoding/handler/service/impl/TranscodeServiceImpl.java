package es.videotranscoding.handler.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.videotranscoding.handler.dto.FFmpegArgumentDTO;
import es.videotranscoding.handler.dto.FlatMediaDTO;
import es.videotranscoding.handler.dto.MediaInfoDTO;
import es.videotranscoding.handler.dto.TranscodeMediaDTO;
import es.videotranscoding.handler.exception.HandlerException;
import es.videotranscoding.handler.model.FlatMedia;
import es.videotranscoding.handler.model.TranscodeMedia;
import es.videotranscoding.handler.service.CommandService;
import es.videotranscoding.handler.service.TranscodeService;
import es.videotranscoding.handler.service.VideoTranscodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TranscodeServiceImpl implements TranscodeService {

    private final CommandService commandServiceImpl;

    private final FlatMediaServiceImpl flatMediaServiceImpl;

    private final TranscodeMediaServiceImpl transcodeMediaServiceImpl;

    private final VideoTranscodeService videoTranscodeServiceImpl;

    @Override
    public void createTranscodes(String uuid, List<FFmpegArgumentDTO> fFmpegArgumentsDTOs) throws HandlerException {
        log.debug("Creating transcodes");
        FlatMedia flatMedia = flatMediaServiceImpl.get(uuid);
        if (Boolean.TRUE.equals(flatMedia.getActive())) {
            log.error("Cannot create transcodes for flatmedia {} active", flatMedia.getId());
            throw new HandlerException("Cannot create transcodes for flatmedia " + flatMedia.getId() + " active");
        }
        switch (flatMedia.getType()) {
            case "audio":
                // TODO:
                throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Audio conversion not implemented");
            case "video":
                videoTranscodeServiceImpl.createTranscodes(flatMedia, fFmpegArgumentsDTOs);
                flatMedia.setActive(true);
                flatMediaServiceImpl.save(flatMedia);
                break;
            default:
                throw new HandlerException("Type of media not found.");
        }
        log.debug("Finish creating transcodes");
    }

    @Override
    public void mediaInfo(FlatMediaDTO flatMediaDTO) throws HandlerException {
        FlatMedia flatMedia = flatMediaServiceImpl.get(flatMediaDTO.getId());
        flatMedia.setActive(true);
        flatMediaServiceImpl.save(flatMedia);
        MediaInfoDTO mediaInfo = commandServiceImpl.getMediaInfo(flatMedia.getPath());
        flatMedia.setBitrate(mediaInfo.getBitrate());
        flatMedia.setFilesize(mediaInfo.getFilesize());
        flatMedia.setDuration(mediaInfo.getDuration());
        flatMedia.setAudioCodec(mediaInfo.getAudioCodec());
        flatMedia.setVideoCodec(mediaInfo.getVideoCodec());
        flatMedia.setResolution(mediaInfo.getResolution());
        flatMedia.setContainer(mediaInfo.getContainer());
        flatMedia.setActive(false);
        flatMediaServiceImpl.save(flatMedia);
    }

    public void mediaInfo(TranscodeMediaDTO transcodeMediaDTO) throws HandlerException {
        Optional<TranscodeMedia> optTranscodeMedia = transcodeMediaServiceImpl.findById(transcodeMediaDTO.getId());
        TranscodeMedia transcodeMedia = null;
        if (optTranscodeMedia.isPresent()) {
            transcodeMedia = optTranscodeMedia.get();
            MediaInfoDTO mediaInfo = commandServiceImpl.getMediaInfo(transcodeMedia.getPath());
            transcodeMedia.setBitrate(mediaInfo.getBitrate());
            transcodeMedia.setFilesize(mediaInfo.getFilesize());
            transcodeMediaServiceImpl.save(transcodeMedia);
        } else {
            log.error("Not found transcodemedia with id {}", transcodeMediaDTO.getId());
            throw new HandlerException("Not found transcodemedia with id " + transcodeMediaDTO.getId());
        }
    }
}