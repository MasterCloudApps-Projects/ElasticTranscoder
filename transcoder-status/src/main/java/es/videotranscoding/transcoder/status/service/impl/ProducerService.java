package es.videotranscoding.transcoder.status.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProducerService {

    @Autowired
    private QueueMessagingTemplate messagingTemplate;

    @Value("${cloud.aws.sqs.endpoint.uri.transcoder-handler}")
    private String QUEUE_TRANSCODER_HANDLER;

    public void sendFinishTranscode(Object message) {
        try {
            log.debug("Sending message to queue transcoder-handler {}", message);
            messagingTemplate.convertAndSend(QUEUE_TRANSCODER_HANDLER, message);
            log.debug("Sended message to queue transcoder-handler");
        } catch (Exception e) {
            log.error("Failed send message to queue handler", e);
        }
    }
}
