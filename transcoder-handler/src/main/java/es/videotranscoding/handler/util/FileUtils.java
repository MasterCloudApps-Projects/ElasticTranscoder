package es.videotranscoding.handler.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import es.videotranscoding.handler.exception.HandlerException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileUtils {

    public void deleteFile(String file) throws HandlerException {
        Path path = Path.of(file);
        try {

            if (Files.isDirectory(path)) {
                try (Stream<Path> files = Files.walk(path)) {
                    if (!files.findAny().isPresent()) {
                        Files.delete(path);
                    }
                }
            } else {
                Files.delete(path);
                // Delete parent if is empty
                deleteFile(path.getParent().toString());
            }
        } catch (IOException e) {
            log.error("File {} not delete", file, e);
            throw new HandlerException("File {} not delete", e);
        }
    }
}