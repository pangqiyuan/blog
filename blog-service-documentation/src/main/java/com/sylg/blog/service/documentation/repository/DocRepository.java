package com.sylg.blog.service.documentation.repository;

import com.sylg.blog.service.documentation.domain.Documentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 博客 Repository
 * @author 忆地球往事
 */
@Repository
public interface DocRepository extends MongoRepository<Documentation,String>, CrudRepository<Documentation,String> {

    List<Documentation> findAllByUsername(String username);

    Documentation findDocumentationByDocNameAndUsername(String docName ,String username);

    Optional<Documentation> findByIdAndIsPublishTrue(String id);

    Page<Documentation> findAllByIsPublishTrue(Pageable pageable);

    List<Documentation> findAllByIsPublishTrue();

}
