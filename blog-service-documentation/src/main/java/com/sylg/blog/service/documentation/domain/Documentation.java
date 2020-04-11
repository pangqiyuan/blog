package com.sylg.blog.service.documentation.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Document(collection = "documentation")
public class Documentation {

    @Id
    @GeneratedValue
    private String id;

    private String username;

    private String docName;

    private String content;

    private String description;

    @CreatedDate
    @Indexed
    private String createTime;

    @LastModifiedDate
    @Indexed
    private String modifyTime;

    private String privatelyOwned;

    private String mainTag;

    private Integer views;

    private List<Tag> tags = new ArrayList<>();

    /**
     * commentBy : {"commentName":"王鑫","commentContent":"牛比","commentTime":"2019-12-20"}
     */

    private List<CommentByBean> commentByBeans;

    @NoArgsConstructor
    @Data
    public static class CommentByBean {
        /**
         * commentName : 王鑫
         * commentContent : 牛比
         * commentTime : 2019-12-20
         */

        private String commentName;
        private String commentContent;
        private String commentTime;
    }
}
