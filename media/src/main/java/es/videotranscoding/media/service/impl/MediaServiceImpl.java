package es.videotranscoding.media.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.videotranscoding.media.dto.FlatMediaDTO;
import es.videotranscoding.media.dto.TranscodeMediaDTO;
import es.videotranscoding.media.exception.MediaException;
import es.videotranscoding.media.model.FlatMedia;
import es.videotranscoding.media.model.TranscodeMedia;
import es.videotranscoding.media.repository.FlatMediaRepository;
import es.videotranscoding.media.repository.TranscodeMediaRepository;
import es.videotranscoding.media.service.MediaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final FlatMediaRepository flatMediaRepository;

    private final TranscodeMediaRepository transcodeMediaRepository;

    private final ModelMapper modelMapper;

    private final ProducerService producerService;

    // TODO: ADMIN and users
    @Override
    public Page<FlatMediaDTO> getAll(Pageable pageable) {
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<FlatMedia> flatMedia;
        if (principal.equals("luisca_jl@hotmail.com")) {
            flatMedia = flatMediaRepository.findAll(pageable);
        } else {
            flatMedia = flatMediaRepository.findByUser(pageable, principal);
        }
        return flatMedia.map(this::flatMediaToDTO);
    }

    @Override
    public FlatMediaDTO get(String uuid) {
        Optional<FlatMedia> flatMedia = flatMediaRepository.findById(uuid);
        if (!flatMedia.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Media not found");
        }
        return flatMediaToDTO(flatMedia.get());
    }

    /**
     * Method to convert flatmedia to dto
     * 
     * @param flatMedia the object
     * @return dto
     */
    private FlatMediaDTO flatMediaToDTO(FlatMedia flatMedia) {
        return modelMapper.map(flatMedia, FlatMediaDTO.class);
    }

    @Override
    public List<TranscodeMediaDTO> getTranscodesMedia(String flatMediaId) {
        return transcodeMediaRepository.findByFlatMediaId(flatMediaId).stream()
                .map(tran -> modelMapper.map(tran, TranscodeMediaDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteFlatMedia(String flatMediaId) throws MediaException {
        Optional<FlatMedia> optFlatMedia = flatMediaRepository.findById(flatMediaId);
        if (optFlatMedia.isPresent()) {
            producerService.sendMessageToDeleteFile(optFlatMedia.get().getPath());
            deleteTranscodeMediaByFlatMediaId(flatMediaId);
            flatMediaRepository.delete(optFlatMedia.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flat media id not found");
        }
    }

    @Override
    public void deleteTranscodeMedia(String transcodeMediaId) throws MediaException {
        Optional<TranscodeMedia> optTranscodeMedia = transcodeMediaRepository.findById(transcodeMediaId);
        if (optTranscodeMedia.isPresent()) {
            producerService.sendMessageToDeleteFile(optTranscodeMedia.get().getPath());
            transcodeMediaRepository.delete(optTranscodeMedia.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TranscodeMedia not found media id not found");
        }
    }

    private void deleteTranscodeMediaByFlatMediaId(String flatMediaId) throws MediaException {
        List<TranscodeMedia> transcodeMedias = transcodeMediaRepository.findByFlatMediaId(flatMediaId);
        for (TranscodeMedia transcodeMedia : transcodeMedias) {
            producerService.sendMessageToDeleteFile(transcodeMedia.getPath());
            transcodeMediaRepository.delete(transcodeMedia);
        }
    }
}
