package es.videotranscoding.storage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

import es.videotranscoding.storage.exception.StorageException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProducerService {

    @Autowired
    private QueueMessagingTemplate messagingTemplate;

    @Value("${cloud.aws.sqs.endpoint.uri.media-info}")
    private String QUEUE_MEDIA_INFO;

    public void sendMessageToGetMediaInfo(Object message) throws StorageException {
        try {
            log.debug("Sending message to queue media-info {}", message);
            messagingTemplate.convertAndSend(QUEUE_MEDIA_INFO, message);
            log.debug("Sended message to queue media-info");
        } catch (Exception e) {
            log.error("Failed send message to queue media-info", e);
            throw new StorageException("Failed send message to queue media-info", e);
        }
    }
}
