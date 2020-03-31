package com.sylg.blog.service.documentation.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Data
@Document(collection = "documentation")
public class Documentation {

    private String _id;
    private String username;
    private String docName;
    private String content;

    @CreatedDate
    @Indexed
    private String createTime;
    @LastModifiedDate
    @Indexed
    private String modifyTime;
    private String privatelyOwned;
    /**
     * commentBy : {"commentName":"王鑫","commentContent":"牛比","commentTime":"2019-12-20"}
     */

    private CommentByBean commentBy;

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
