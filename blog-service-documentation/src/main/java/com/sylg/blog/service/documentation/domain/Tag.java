package com.sylg.blog.service.documentation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

/**
 * @program: blog
 * @description: 博客标签
 * @author: 忆地球往事
 * @create: 2020-04-08 16:33
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "documentation_tag")
public class Tag {

    @Id
    @GeneratedValue
    private String tid;

    private String name;

    private Boolean isMainTag;

    private Integer blogNumber;

    private List<String> blogId;


}
