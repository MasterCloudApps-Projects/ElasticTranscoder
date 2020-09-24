package es.videotranscoding.handler.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import es.videotranscoding.handler.model.TranscodeMedia;

@Repository
@EnableScan
public interface TranscodeMediaRepository extends PagingAndSortingRepository<TranscodeMedia, String> {

}