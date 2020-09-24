package es.videotranscoding.storage.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import es.videotranscoding.storage.model.FlatMedia;

@Repository
@EnableScan
public interface FlatMediaRepository extends PagingAndSortingRepository<FlatMedia, String> {
    
}