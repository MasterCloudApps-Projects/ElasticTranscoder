package es.videotranscoding.storage.util;

import java.util.Arrays;

import org.apache.commons.io.FilenameUtils;

import es.videotranscoding.storage.exception.StorageException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils {

    private FileUtils() {
    }

    public static boolean isVideoFile(String fileName) {
        String fileExtension = FilenameUtils.getExtension(fileName.toLowerCase());
        return Arrays.asList(VideoExtensions.values()).stream()
                .anyMatch(videoExtension -> videoExtension.toString().equalsIgnoreCase(fileExtension));
    }

    public static boolean isAudioFile(String fileName) {
        String fileExtension = FilenameUtils.getExtension(fileName.toLowerCase());
        return Arrays.asList(AudioExtensions.values()).stream()
                .anyMatch(videoExtension -> videoExtension.toString().equalsIgnoreCase(fileExtension));
    }

    /**
     * Method to delete file if exists and the parent folder if is empty
     * 
     * @param file
     * @throws StorageException
     */
    public static void deleteFile(String file) throws StorageException {
        Path path = Path.of(file);
        try {
            if (Files.exists(path)) {
                if (Files.isDirectory(path)) {
                    try (Stream<Path> files = Files.walk(path)) {
                        if (!files.findAny().isPresent()) {
                            Files.delete(path);
                        }
                    }
                } else {
                    Files.delete(path);
                    deleteFile(path.getParent().toString());
                }
            }
        } catch (IOException e) {
            log.error("File {} not delete", file, e);
            throw new StorageException("File {} not delete", e);
        }
    }

}