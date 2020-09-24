package es.videotranscoding.transcoder.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import es.videotranscoding.transcoder.dto.TranscodeMediaDTO;
import es.videotranscoding.transcoder.exception.TranscoderException;
import es.videotranscoding.transcoder.service.TranscodeService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsumerService {

    @Autowired
    private TranscodeService transcodeServiceImpl;

    @SqsListener(value = "transcoder", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void consumeMessage(String message, @Header("SenderId") String senderId) throws TranscoderException {
        log.debug("Reading message of the queue transcoder: {}", message);
        ObjectMapper mapper = new ObjectMapper();
        TranscodeMediaDTO transcode = null;
        try {
            transcode = mapper.readValue(message, TranscodeMediaDTO.class);
        } catch (JsonProcessingException e) {
            log.error("The message cannot convert to TranscodeMediaDTO", e);
            throw new TranscoderException("The message cannot convert to TranscodeMediaDTO", e);
        }
        if (transcodeServiceImpl.transcode(transcode) != 0) {
            log.error("Error transcoding this segment {} ", transcode.toString());
            throw new TranscoderException("Error transcoding this segment returning to queue: " + transcode.toString());
        }
        log.debug("Work message finished");
    }

}