package es.videotranscoding.transcoder.status.service;

import java.util.List;

import es.videotranscoding.transcoder.status.dto.TranscodeMediaStatusDTO;

public interface WebsocketService {

    void sendStatus(List<TranscodeMediaStatusDTO> transcodeMediaDTO);

}
