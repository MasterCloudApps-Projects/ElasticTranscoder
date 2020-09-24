package es.videotranscoding.storage.service.impl;

import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import es.videotranscoding.storage.exception.StorageException;
import es.videotranscoding.storage.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsumerService {

    @SqsListener(value = "delete-file", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void deleteFile(String message, @Header("SenderId") String senderId) throws StorageException {
        log.debug("Reading message of the queue delete-file: {}", message);
        FileUtils.deleteFile(message);
        log.debug("Delete file  finished");
    }

}