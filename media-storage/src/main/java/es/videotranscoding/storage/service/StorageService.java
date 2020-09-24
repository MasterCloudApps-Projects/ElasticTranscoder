package es.videotranscoding.storage.service;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

import es.videotranscoding.storage.dto.FlatMediaDTO;
import es.videotranscoding.storage.exception.StorageException;

public interface StorageService {

    FlatMediaDTO store(MultipartFile file) throws StorageException;

    //FIXME: ConsumerService must use this method
    void delete(String path);

    Path getPathFile(String fileId);

}