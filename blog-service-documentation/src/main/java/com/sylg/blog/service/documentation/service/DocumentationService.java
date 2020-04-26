package com.sylg.blog.service.documentation.service;

import com.sylg.blog.service.documentation.domain.Documentation;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author pangqiyuan
 * @date 2020/3/29
 *
 * blog service
 */
public interface DocumentationService {

    /**
    * @Description: 根据用户名查询blog
    * @Param: [username]
    * @return: java.util.List<com.sylg.blog.service.documentation.domain.Documentation>
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    List<Documentation> findAllByName(String username);

    /**
    * @Description: 添加blog
    * @Param: [documentation]
    * @return: void
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    void insertDoc(Documentation documentation);

    /**
    * @Description: 查询所有文档
    * @Param: []
    * @return: java.util.List<com.sylg.blog.service.documentation.domain.Documentation>
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    List<Documentation> findAll();

    /**
     * @Description: 查询所有审核通过的文档
     * @Param: []
     * @return: java.util.List<com.sylg.blog.service.documentation.domain.Documentation>
     * @Author: 忆地球往事
     * @Date: 2020/4/24
     */
    List<Documentation> findAllByIsPublishTrue();

    /**
    * @Description: 根据标题查询文档
    * @Param: [docName, username]
    * @return: com.sylg.blog.service.documentation.domain.Documentation
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    Documentation findDocByDocName(String docName,String username);
    
    /**
    * @Description: 根据标题保存文档
    * @Param: [documentation, request]
    * @return: void
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    void saveDocByDocName(Documentation documentation, HttpServletRequest request);

    /**
    * @Description: 根据id删除文档
    * @Param: [docId]
    * @return: void
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    void deleteDocByDocName(String docId);

    /**
    * @Description: 分页查询所有文档
    * @Param: [pageable]
    * @return: java.util.List<com.sylg.blog.service.documentation.domain.Documentation>
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    List<Documentation> findAll(Pageable pageable);

    /**
    * @Description: 根据id查询文档
    * @Param: [id]
    * @return: com.sylg.blog.service.documentation.domain.Documentation
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    Documentation findDocById(String id);

    /**
    * @Description: 根据id批量查询发布文档
    * @Param: [ids]
    * @return: java.util.List<com.sylg.blog.service.documentation.domain.Documentation>
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    List<Documentation> findDocByIdsAndIsPublishTrue(List<String> ids);

    /**
     * @Description: 根据id批量查询文档
     * @Param: [ids]
     * @return: java.util.List<com.sylg.blog.service.documentation.domain.Documentation>
     * @Author: 忆地球往事
     * @Date: 2020/4/24
     */
    List<Documentation> findDocByIds(List<String> ids);

    /**
    * @Description: 批量查询博客通过id并通过观看量排序
    * @Param: [ids, limit]
    * @return: java.util.List<com.sylg.blog.service.documentation.domain.Documentation>
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    List<Documentation> findDocByIdsAndViewsSort(List<String> ids,Integer limit);

    /**
    * @Description: 保存博客评论
    * @Param: [id, commentByBean]
    * @return: java.lang.Boolean
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    Boolean saveCommentByDocId(String id , Documentation.CommentByBean commentByBean);

    /**
    * @Description: 保存博客评论回复
    * @Param: [id, replyComment, replyCid]
    * @return: java.lang.Boolean
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    Boolean saveCommentByDocId(String id, Documentation.ReplyComment replyComment,String replyCid);

    /**
    * @Description: 查询博客通过分数排序
    * @Param: [limit]
    * @return: java.util.List<com.sylg.blog.service.documentation.domain.Documentation>
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    List<Documentation> findDocByScoreDesc(Integer limit);

    /**
    * @Description: 查询博客通过关键字
    * @Param: [keyword]
    * @return: java.util.List<com.sylg.blog.service.documentation.domain.Documentation>
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    List<Documentation> searchByKeyword(String keyword);

    /**
    * @Description: 随机查询博客
    * @Param: [randomCount]
    * @return: java.util.List<com.sylg.blog.service.documentation.domain.Documentation>
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    List<Documentation> findDocByRandom(Integer randomCount);

    /**
    * @Description: 通过评论数排序查询博客
    * @Param: [limit]
    * @return: java.util.List<com.sylg.blog.service.documentation.domain.Documentation>
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    List<Documentation> findDocByCommentDesc(Integer limit);

    /**
    * @Description: 查询推荐博客
    * @Param: [limit]
    * @return: java.util.List<com.sylg.blog.service.documentation.domain.Documentation>
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    List<Documentation> findDocByRecommend(Integer limit);

    /**
    * @Description: 审核博客
    * @Param: [id]
    * @return: void
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    void reviewDoc(String id);

    /**
     * @Description: 发布博客
     * @Param: [id]
     * @return: void
     * @Author: 忆地球往事
     * @Date: 2020/4/24
     */
    void publishDoc(String id);
}
