package es.videotranscoding.media.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

import es.videotranscoding.media.exception.MediaException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProducerService {

    @Autowired
    private QueueMessagingTemplate messagingTemplate;

    @Value("${cloud.aws.sqs.endpoint.uri.delete-file}")
    private String QUEUE_DELETE_FILE;

    public void sendMessageToDeleteFile(Object message) throws MediaException {
        if (Objects.nonNull(message)) {
            try {
                log.debug("Sending message to queue delete-file {}", message);
                messagingTemplate.convertAndSend(QUEUE_DELETE_FILE, message);
                log.debug("Sended message to queue delete-file");
            } catch (Exception e) {
                log.error("Failed send message to queue delete-file", e);
                throw new MediaException("Failed send message to queue delete-file", e);
            }
        }
    }

}
