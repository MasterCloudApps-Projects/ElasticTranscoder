package es.videotranscoding.transcoder.status.service.impl;

import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import es.videotranscoding.transcoder.status.dto.TranscodeMediaDTO;
import es.videotranscoding.transcoder.status.exception.TranscoderStatusException;
import es.videotranscoding.transcoder.status.model.FlatMedia;
import es.videotranscoding.transcoder.status.model.TranscodeMedia;
import es.videotranscoding.transcoder.status.repository.FlatMediaRepository;
import es.videotranscoding.transcoder.status.repository.TranscodeMediaRepository;
import es.videotranscoding.transcoder.status.service.TranscoderStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TranscoderStatusServiceImpl implements TranscoderStatusService {

    private final TranscodeMediaRepository transcodeMediaRepository;

    private final FlatMediaRepository flatMediaRepository;

    private final ProducerService producerService;

    @Override
    public void update(TranscodeMediaDTO transcodeMediaDTO) throws TranscoderStatusException {
        Optional<TranscodeMedia> optTranscodeMedia = transcodeMediaRepository.findById(transcodeMediaDTO.getId());
        TranscodeMedia transcodeMedia = null;
        if (!optTranscodeMedia.isPresent()) {
            log.error("Not exists {} on transcode_media table", transcodeMediaDTO.getId());
            throw new TranscoderStatusException(
                    "Not exists " + transcodeMediaDTO.getId() + " on transcode_media table");
        } else {
            transcodeMedia = optTranscodeMedia.get();
        }
        transcodeMedia.setProcessed(roundAvoid(transcodeMediaDTO.getProcessed(), 2));
        if (transcodeMedia.getProcessed() == 100) {
            transcodeMedia.setActive(false);
            checkIfLastTranscode(transcodeMedia);
            producerService.sendFinishTranscode(transcodeMedia);
        } else {
            transcodeMedia.setActive(true);
        }
        transcodeMediaRepository.save(transcodeMedia);
    }

    private double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    private void checkIfLastTranscode(TranscodeMedia transcodeMedia) {
        if (StreamSupport
                .stream(transcodeMediaRepository.findByFlatMediaId(transcodeMedia.getFlatMediaId()).spliterator(),
                        false)
                .filter(transcodeMediaS -> Double.valueOf(transcodeMediaS.getProcessed())
                        .equals(Double.parseDouble("100")))
                .findAny().isEmpty()) {
            Optional<FlatMedia> optFlatMedia = flatMediaRepository.findById(transcodeMedia.getFlatMediaId());
            if (optFlatMedia.isPresent()) {
                optFlatMedia.get().setActive(false);
                flatMediaRepository.save(optFlatMedia.get());
            }
        }
    }
}
