package es.videotranscoding.handler.restcontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.videotranscoding.handler.dto.FFmpegArgumentsDTO;
import es.videotranscoding.handler.exception.HandlerException;
import es.videotranscoding.handler.dto.FFmpegArgumentDTO;
import es.videotranscoding.handler.service.TranscodeService;
import es.videotranscoding.handler.service.impl.FfmpegArgumentsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/transcodes")
@RestController
@RequiredArgsConstructor
@CrossOrigin({ "http://localhost:4200", "https://lavandadelpatio.es" })
@Slf4j
public class TranscoderRestController {

    private final TranscodeService transcodeServiceImpl;

    private final FfmpegArgumentsServiceImpl ffpmegArgumentsServiceImpl;

    @GetMapping("/{type}")
    public ResponseEntity<FFmpegArgumentsDTO> getTypes(@PathVariable String type) {
        return ResponseEntity.ok().body(ffpmegArgumentsServiceImpl.getTranscodes(type));
    }

    @PostMapping("/{uuid}")
    public ResponseEntity<Void> createTranscodes(@PathVariable String uuid,
            @RequestBody List<FFmpegArgumentDTO> transcodesData) throws HandlerException {
        log.debug("Creating transcodes for uuid {}", uuid);
        transcodeServiceImpl.createTranscodes(uuid, transcodesData);
        log.debug("Transcodes created for uuid {}", uuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}