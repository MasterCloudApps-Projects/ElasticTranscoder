package es.videotranscoding.handler.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.videotranscoding.handler.model.FlatMedia;
import es.videotranscoding.handler.repository.FlatMediaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlatMediaServiceImpl {

    private final FlatMediaRepository flatMediaRepository;

    public FlatMedia get(String uuid) {
        return flatMediaRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flat media not found"));
    }

    public FlatMedia save(FlatMedia flatMedia) {
        return flatMediaRepository.save(flatMedia);
    }
}