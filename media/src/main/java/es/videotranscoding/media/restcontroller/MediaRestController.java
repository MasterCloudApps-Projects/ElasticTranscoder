package es.videotranscoding.media.restcontroller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.videotranscoding.media.dto.FlatMediaDTO;
import es.videotranscoding.media.dto.TranscodeMediaDTO;
import es.videotranscoding.media.exception.MediaException;
import es.videotranscoding.media.service.MediaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
@CrossOrigin({ "http://localhost:4200", "https://videotranscoding.es" })
public class MediaRestController {

    private final MediaService mediaServiceImpl;

    @GetMapping
    public ResponseEntity<Page<FlatMediaDTO>> getAllMedia(Pageable pageable) {
        return ResponseEntity.ok().body(mediaServiceImpl.getAll(pageable));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<FlatMediaDTO> getAllMedia(@PathVariable String uuid) {
        return ResponseEntity.ok().body(mediaServiceImpl.get(uuid));
    }

    @GetMapping("/{flatMediaId}/transcodes")
    public ResponseEntity<List<TranscodeMediaDTO>> getTranscodes(@PathVariable String flatMediaId) {
        return ResponseEntity.ok().body(mediaServiceImpl.getTranscodesMedia(flatMediaId));
    }

    @DeleteMapping("/{flatMediaId}")
    public ResponseEntity<Void> deleteFlatMedia(@PathVariable String flatMediaId) throws MediaException {
        mediaServiceImpl.deleteFlatMedia(flatMediaId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/transcodes/{transcodeMediaId}")
    public ResponseEntity<Void> deleteTranscode(@PathVariable String transcodeMediaId) throws MediaException {
        mediaServiceImpl.deleteTranscodeMedia(transcodeMediaId);
        return ResponseEntity.ok().build();
    }
}
