package es.videotranscoding.transcoder.status.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import es.videotranscoding.transcoder.status.dto.TranscodeMediaStatusDTO;
import es.videotranscoding.transcoder.status.model.TranscodeMedia;
import es.videotranscoding.transcoder.status.repository.TranscodeMediaRepository;
import es.videotranscoding.transcoder.status.service.WebsocketService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WebsocketServiceImpl implements WebsocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private TranscodeMediaRepository transcodeMediaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @SneakyThrows(MessagingException.class)
    public void sendStatus(List<TranscodeMediaStatusDTO> transcodeMediaDTO) {
        simpMessagingTemplate.convertAndSend("/transcodes", transcodeMediaDTO);
    }

    @Scheduled(fixedDelay = 4000)
    private void scheduleWebsocket() {
        log.debug("Sending message to WS");
        List<TranscodeMedia> transcodeMedias = StreamSupport
                .stream(transcodeMediaRepository.findAll().spliterator(), false)
                .filter(TranscodeMedia::getActive)
                .collect(Collectors.toList());
        sendStatus(transcodeMedias.stream().map(tm -> modelMapper.map(tm, TranscodeMediaStatusDTO.class))
                .collect(Collectors.toList()));
        log.debug("Sended message to WS");
    }
}
