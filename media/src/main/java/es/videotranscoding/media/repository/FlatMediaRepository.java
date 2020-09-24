package es.videotranscoding.media.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.videotranscoding.media.model.FlatMedia;

@EnableScanCount
@EnableScan
public interface FlatMediaRepository extends PagingAndSortingRepository<FlatMedia, String> {

    Page<FlatMedia> findByUser(Pageable page, String user);
}