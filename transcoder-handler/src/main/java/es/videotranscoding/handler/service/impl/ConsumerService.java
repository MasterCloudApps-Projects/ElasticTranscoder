package es.videotranscoding.handler.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import es.videotranscoding.handler.dto.FlatMediaDTO;
import es.videotranscoding.handler.dto.TranscodeMediaDTO;
import es.videotranscoding.handler.exception.HandlerException;
import es.videotranscoding.handler.service.TranscodeService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsumerService {

    private final String JSON_PROCESSING_EXCEPTION = "The message cannot convert to FlatMediaDTO";
    private final String WORK_MESSAGE_FINISHED = "Work message finished";


    @Autowired
    private TranscodeService transcodeServiceImpl;

    @SqsListener(value = "media-info", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void mediaInfo(String message, @Header("SenderId") String senderId) throws HandlerException {
        log.debug("Reading message of the queue media-info: {}", message);
        ObjectMapper mapper = new ObjectMapper();
        FlatMediaDTO flatMedia = null;
        try {
            flatMedia = mapper.readValue(message, FlatMediaDTO.class);
        } catch (JsonProcessingException e) {
            log.error(JSON_PROCESSING_EXCEPTION, e);
            throw new HandlerException(JSON_PROCESSING_EXCEPTION, e);
        }
        transcodeServiceImpl.mediaInfo(flatMedia);
        log.debug(WORK_MESSAGE_FINISHED);
    }

    @SqsListener(value = "transcoder-handler", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void getFinishTranscode(String message, @Header("SenderId") String senderId) throws HandlerException {
        log.debug("Reading message of the queue transcoder-handler: {}", message);
        ObjectMapper mapper = new ObjectMapper();
        TranscodeMediaDTO transcodeMedia = null;
        try {
            transcodeMedia = mapper.readValue(message, TranscodeMediaDTO.class);
        } catch (JsonProcessingException e) {
            log.error(JSON_PROCESSING_EXCEPTION, e);
            throw new HandlerException(JSON_PROCESSING_EXCEPTION, e);
        }
        transcodeServiceImpl.mediaInfo(transcodeMedia);
        log.debug(WORK_MESSAGE_FINISHED);
    }

}