package es.videotranscoding.storage.restcontroller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import es.videotranscoding.storage.dto.FlatMediaDTO;
import es.videotranscoding.storage.exception.StorageException;
import es.videotranscoding.storage.service.StorageService;
import es.videotranscoding.storage.util.VideoStreamService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@CrossOrigin({ "http://localhost:4200", "https://lavandadelpatio.es" })
public class FilesRestController {

    private final StorageService storageService;

    private final VideoStreamService videoStreamService;

    @PostMapping
    public ResponseEntity<List<FlatMediaDTO>> uploadFile(@RequestParam(value = "file") Set<MultipartFile> files)
            throws StorageException {
        List<FlatMediaDTO> flatMediaDTOs = new ArrayList<>();
        for (MultipartFile file : files) {
            flatMediaDTOs.add(storageService.store(file));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(flatMediaDTOs);
    }

    @GetMapping("/player/{id}")
    public Mono<ResponseEntity<byte[]>> watchFile(@PathVariable String id,
            @RequestHeader(value = "Range", required = false) String httpRangeList) {
        Path path = storageService.getPathFile(id);
        return Mono.just(videoStreamService.prepareContent(path.toString(), FilenameUtils.getExtension(path.toString()),
                httpRangeList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingResponseBody> downloadFile(@PathVariable String id) {
        Path path = storageService.getPathFile(id);
        StreamingResponseBody body = out -> {
            Files.copy(path, out);
        };
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("filename", path.getFileName().toString());
        return ResponseEntity.ok().headers(headers).contentType(MediaType.valueOf("application/force-download"))
                .body(body);
    }
}
