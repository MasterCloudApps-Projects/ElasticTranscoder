package es.videotranscoding.media.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.videotranscoding.media.dto.FlatMediaDTO;
import es.videotranscoding.media.dto.TranscodeMediaDTO;
import es.videotranscoding.media.exception.MediaException;

public interface MediaService {

    Page<FlatMediaDTO> getAll(Pageable pageable);

    FlatMediaDTO get(String uuid);

    List<TranscodeMediaDTO> getTranscodesMedia(String flatMediaId);

    void deleteFlatMedia(String flatMediaId) throws MediaException;

    void deleteTranscodeMedia(String transcodeMediaId) throws MediaException;
}