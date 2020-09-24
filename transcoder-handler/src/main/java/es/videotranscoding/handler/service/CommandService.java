package es.videotranscoding.handler.service;

import es.videotranscoding.handler.dto.MediaInfoDTO;
import es.videotranscoding.handler.exception.HandlerException;

public interface CommandService {

    /**
     * Using FFPROBE
     * 
     * @param path
     * @return
     * @throws HandlerException
     */
    MediaInfoDTO getMediaInfo(String path) throws HandlerException;

}