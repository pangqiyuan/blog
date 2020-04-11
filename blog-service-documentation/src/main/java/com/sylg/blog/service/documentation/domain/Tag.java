package com.sylg.blog.service.documentation.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: blog
 * @description: 博客标签
 * @author: 忆地球往事
 * @create: 2020-04-08 16:33
 **/
@NoArgsConstructor
@Data
@Document(collection = "documentation_tag")
public class Tag {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    private List<Documentation> blogs = new ArrayList<>();
}
