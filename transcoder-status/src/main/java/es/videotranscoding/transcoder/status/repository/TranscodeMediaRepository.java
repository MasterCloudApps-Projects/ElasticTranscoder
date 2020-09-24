package es.videotranscoding.transcoder.status.repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import es.videotranscoding.transcoder.status.model.TranscodeMedia;

@Repository
@EnableScan
public interface TranscodeMediaRepository extends PagingAndSortingRepository<TranscodeMedia, String> {

    List<TranscodeMedia> findByFlatMediaId(String flatMediaId);

}