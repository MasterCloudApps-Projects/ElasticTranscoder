package es.videotranscoding.handler.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import es.videotranscoding.handler.model.TranscodeMedia;
import es.videotranscoding.handler.repository.TranscodeMediaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TranscodeMediaServiceImpl {

    private final TranscodeMediaRepository transcodeMediaRepository;

    public TranscodeMedia save(TranscodeMedia transcodeMedia) {
        return transcodeMediaRepository.save(transcodeMedia);
    }

    public Optional<TranscodeMedia> findById(String transcodeMediaId) {
        return transcodeMediaRepository.findById(transcodeMediaId);
    }
}