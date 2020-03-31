package com.sylg.blog.service.documentation.repository;

import com.sylg.blog.service.documentation.domain.Documentation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocRepository extends MongoRepository<Documentation,String>, CrudRepository<Documentation,String> {

    List<Documentation> findAllByUsername(String username);

    Documentation findDocumentationByDocNameAndUsername(String docName ,String username);

}
