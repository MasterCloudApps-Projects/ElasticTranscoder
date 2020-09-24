package es.videotranscoding.handler.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import es.videotranscoding.handler.model.FlatMedia;


@Repository
@EnableScan
public interface FlatMediaRepository extends PagingAndSortingRepository<FlatMedia, String> {
}