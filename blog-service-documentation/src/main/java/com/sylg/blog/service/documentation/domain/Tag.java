package com.sylg.blog.service.documentation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

    /**
     * 标签id
     */
    @Id
    @GeneratedValue
    @Field("_id")
    private String tid;

    /**
     * 标签名
     */
    private String name;

    /**
     * 是否为主标签
     */
    private Boolean isMainTag;

    /**
     * 标签博客数量
     */
    private Integer blogNumber;

    /**
     * 所属博客
     */
    private List<String> blogId;


}
