package com.sylg.blog.service.documentation.repository;

import com.sylg.blog.service.documentation.domain.Documentation;
import com.sylg.blog.service.documentation.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends MongoRepository<Tag,String>, CrudRepository<Tag,String> {

    Optional<Tag> findByName(String name);

    Optional<List<Tag>> findAllByIsMainTagIsTrue();
}
