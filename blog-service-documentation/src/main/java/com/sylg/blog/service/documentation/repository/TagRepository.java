package com.sylg.blog.service.documentation.repository;

import com.sylg.blog.service.documentation.domain.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 标签 Repository
 * @author 忆地球往事
 */
@Repository
public interface TagRepository extends MongoRepository<Tag,String>, CrudRepository<Tag,String> {

    Optional<Tag> findByName(String name);

    Optional<List<Tag>> findAllByIsMainTagIsTrue();

    Optional<List<Tag>> findAllByIsMainTagIsFalse();
}
