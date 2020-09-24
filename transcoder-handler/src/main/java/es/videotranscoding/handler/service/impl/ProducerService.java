package es.videotranscoding.handler.service.impl;

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

    @Value("${cloud.aws.sqs.endpoint.uri.transcoder}")
    private String QUEUE_TRANSCODER;

    @Value("${cloud.aws.sqs.endpoint.uri.transcoder-hls}")
    private String QUEUE_TRANSCODER_HLS;

    public void sendTranscode(Object object) {
        try {
            log.debug("Sending message to queue transcoder");
            messagingTemplate.convertAndSend(QUEUE_TRANSCODER, object);
            log.debug("Sended");

        } catch (Exception e) {
            log.error("Failed send message to queue transcoder", e);
        }
    }
}
