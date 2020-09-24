package es.videotranscoding.media.repository;

import java.util.List;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.repository.PagingAndSortingRepository;
import es.videotranscoding.media.model.TranscodeMedia;

@EnableScanCount
@EnableScan
public interface TranscodeMediaRepository
        extends PagingAndSortingRepository<TranscodeMedia, String> {

    List<TranscodeMedia> findByFlatMediaId(String flatMediaId);

    List<TranscodeMedia> findByUser(String user);
}
