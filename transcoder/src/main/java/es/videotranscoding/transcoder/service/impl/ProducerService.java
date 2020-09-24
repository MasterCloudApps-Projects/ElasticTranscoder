package es.videotranscoding.transcoder.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;
import es.videotranscoding.transcoder.exception.TranscoderException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProducerService {

    @Autowired
    private QueueMessagingTemplate messagingTemplate;

    @Value("${cloud.aws.sqs.endpoint.uri.transcoder-status}")
    private String QUEUE_TRANSCODER_STATUS;

    public void sendStatus(Object message) throws TranscoderException {
        try {
            log.debug("Sending message to queue transcoder-status {}", message);
            Map<String, Object> headers = new HashMap<>();
            headers.put("message-group-id", "transcoder-status-fifo");
            messagingTemplate.convertAndSend(QUEUE_TRANSCODER_STATUS, message, headers);
            log.debug("Sended message to queue transcoder-status");
        } catch (Exception e) {
            log.error("Failed send message to queue transcoder-status", e);
            throw new TranscoderException("Failed send message to queue transcoder-status", e);
        }
    }
}
