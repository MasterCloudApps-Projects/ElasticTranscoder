package es.videotranscoding.transcoder.status.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import es.videotranscoding.transcoder.status.dto.TranscodeMediaDTO;
import es.videotranscoding.transcoder.status.exception.TranscoderStatusException;
import es.videotranscoding.transcoder.status.service.TranscoderStatusService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsumerService {

    @Autowired
    private TranscoderStatusService transcoderStatusServiceImpl;

    @SqsListener(value = "transcoder-status.fifo", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
    public void consumeMessage(String message, @Header("SenderId") String senderId) throws TranscoderStatusException {
        log.debug("Reading message of the queue transcoder-status: {}", message);
        ObjectMapper mapper = new ObjectMapper();
        TranscodeMediaDTO transcode = null;
        try {
            transcode = mapper.readValue(message, TranscodeMediaDTO.class);
        } catch (JsonProcessingException e) {
            log.error("The message cannot convert to TranscodeMediaDTO", e);
            throw new TranscoderStatusException("The message cannot convert to TranscodeMediaDTO", e);
        }
        transcoderStatusServiceImpl.update(transcode);
        log.debug("Work message finished");
    }
}
