package com.sylg.blog.service.documentation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import java.util.Set;

/**
 * 博客类
 * @author 忆地球往事
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
@Document(collection = "documentation")
public class Documentation {

    /**
     * 博客id
     */
    @Id
    @GeneratedValue
    private String id;

    /**
     * 博客作者
     */
    private String username;

    /**
     * 博客标题
     */
    private String docName;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 博客描述
     */
    private String description;

    /**
     * 博客创建时间
     */
    @CreatedDate
    @Indexed
    private String createTime;


    /**
     * 博客修改时间
     */
    @LastModifiedDate
    @Indexed
    private String modifyTime;

    /**
     * 博客公开
     */
    private String privatelyOwned;

    /**
     * 博客主标签
     */
    private String mainTag;

    /**
     * 博客观看量
     */
    private Integer views;

    /**
     * 博客标签
     */
    private Set<Tag> tags;

    /**
     * 博客是否可以评论
     */
    private Boolean isComment;

    /**
     * 博客分数
     */
    private Double score;

    /**
     * 博客是否发布
     */
    private Boolean isPublish;

    /**
     * 博客评论
     * commentBy : {"commentName":"王鑫","commentContent":"牛比","commentTime":"2019-12-20"}
     */
    private List<CommentByBean> commentByBeans ;

    /**
     * 博客评论数
     */
    private Integer commentSize;

    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    public static class CommentByBean {
        /**
         * commentName : 王鑫
         * commentContent : 牛比
         * commentTime : 2019-12-20
         */
        private String cid;
        private String commentName;
        private String commentContent;
        private String commentTime;

        /**
         * 评论回复
         */
        private List<ReplyComment> replyComment;
    }

    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    public static class ReplyComment {

        private String rid;
        private String replyName;
        private String commentName;
        private String commentContent;
        private String commentTime;
    }

}
