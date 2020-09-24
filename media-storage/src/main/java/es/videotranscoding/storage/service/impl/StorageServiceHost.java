package es.videotranscoding.storage.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import es.videotranscoding.storage.dto.FlatMediaDTO;
import es.videotranscoding.storage.exception.StorageException;
import es.videotranscoding.storage.model.FlatMedia;
import es.videotranscoding.storage.model.TranscodeMedia;
import es.videotranscoding.storage.repository.FlatMediaRepository;
import es.videotranscoding.storage.repository.TranscodeMediaRepository;
import es.videotranscoding.storage.service.StorageService;
import es.videotranscoding.storage.util.AudioExtensions;
import es.videotranscoding.storage.util.FileUtils;
import es.videotranscoding.storage.util.VideoExtensions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Profile({ "host" })
@Service
@Slf4j
@RequiredArgsConstructor
public class StorageServiceHost implements StorageService {

    @Value("${path.media.flat}")
    private Path flatMediaFolder;

    @Value("${path.media.transcode}")
    private Path transcodeMediaFolder;

    private final FlatMediaRepository flatMediaRepository;

    private final TranscodeMediaRepository transcodeMediaRepository;

    private final ProducerService producerService;

    private final ModelMapper modelMapper;

    private final String VIDEO = "video";
    private final String AUDIO = "audio";

    @PostConstruct
    public void postConstruct() throws StorageException {
        if (!Files.exists(flatMediaFolder)) {
            try {
                log.debug("Creating directory on {}", flatMediaFolder.toString());
                Files.createDirectories(flatMediaFolder);
            } catch (IOException e) {
                log.error("Cannot create flat media folder {}", flatMediaFolder.toString(), e);
                throw new StorageException("Cannot create transcode media folder " + flatMediaFolder.toString(), e);
            }
        }
        if (!Files.exists(transcodeMediaFolder)) {
            try {
                log.debug("Creating directory on {}", transcodeMediaFolder.toString());
                Files.createDirectories(transcodeMediaFolder);
            } catch (IOException e) {
                log.error("Cannot create flat media folder {}", transcodeMediaFolder.toString(), e);

                throw new StorageException("Cannot create transcode media folder " + transcodeMediaFolder.toString(),
                        e);
            }
        }
    }

    @Override
    public FlatMediaDTO store(MultipartFile multipartFile) throws StorageException {
        Path pathFile = null;
        FlatMedia flatMedia = FlatMedia.builder().build();
        try {
            if (StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
                throw new StorageException("NO PERMITIDO");
            }
            if (FileUtils.isVideoFile(multipartFile.getOriginalFilename())) {
                flatMedia.setType(VIDEO);
            } else if (FileUtils.isAudioFile(multipartFile.getOriginalFilename())) {
                flatMedia.setType(AUDIO);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "File type not permitted. Values permitted are "
                                + Arrays.asList(VideoExtensions.values()).toString()
                                + Arrays.asList(AudioExtensions.values()));
            }
            Path pathFolder = Path.of(flatMediaFolder.toString(),
                    FilenameUtils.getBaseName(multipartFile.getOriginalFilename()).replace(" ", "_") + "_"
                            + System.currentTimeMillis());
            if (!Files.exists(pathFolder)) {
                Files.createDirectories(pathFolder);
            }
            pathFile = Path.of(pathFolder.toString(), multipartFile.getOriginalFilename());
            Files.copy(multipartFile.getInputStream(), pathFile, StandardCopyOption.REPLACE_EXISTING);
            flatMedia.setName(FilenameUtils.getBaseName(multipartFile.getOriginalFilename()));
            flatMedia.setPath(pathFile.toString());
            flatMedia.setUser(SecurityContextHolder.getContext().getAuthentication().getName());
            FlatMediaDTO flatMediaDTO = modelMapper.map(flatMediaRepository.save(flatMedia), FlatMediaDTO.class);
            producerService.sendMessageToGetMediaInfo(flatMediaDTO);
            return modelMapper.map(flatMediaRepository.save(flatMedia), FlatMediaDTO.class);
        } catch (IOException e) {
            log.error("IOException on storage service", e);
            try {
                // TODO Check if is null
                Files.deleteIfExists(pathFile);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            // TODO FIX THEM
            throw new StorageException("IOException on storage service");
        } finally {
            // Path pathFile;
            // TODO: FIX THIS
        }
    }

    @Override
    public void delete(String fileId) {
        // TODO Auto-generated method stub

    }

    @Override
    public Path getPathFile(String fileId) {
        Optional<FlatMedia> optFlatMedia = flatMediaRepository.findById(fileId);
        if (optFlatMedia.isPresent()) {
            return Paths.get(optFlatMedia.get().getPath());
        } else {
            Optional<TranscodeMedia> optTranscodeMedia = transcodeMediaRepository.findById(fileId);
            if (optTranscodeMedia.isPresent()) {
                return Paths.get(optTranscodeMedia.get().getPath());
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found this fileId");
    }

}