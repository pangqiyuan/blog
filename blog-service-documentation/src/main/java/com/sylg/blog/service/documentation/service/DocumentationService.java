package com.sylg.blog.service.documentation.service;

import com.sylg.blog.service.documentation.domain.Documentation;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DocumentationService {

    List<Documentation> findAllByName(String username);

    void insertDoc(Documentation documentation);

    List<Documentation> findAll();

    Documentation findDocByDocName(String docName,String username);

    void saveDocByDocName(Documentation documentation);

    void deleteDocByDocName(String docId);

    List<Documentation> findAll(Pageable pageable);

    Documentation findDocById(String id);

    List<Documentation> findDocByIds(List<String> ids);

    Boolean saveCommentByDocId(String id , Documentation.CommentByBean commentByBean);

    Boolean saveCommentByDocId(String id, Documentation.ReplyComment replyComment,String replyCid);

    List<Documentation> findDocByScoreDesc(Integer limit);

    List<Documentation> searchByKeyword(String keyword);

    List<Documentation> findDocByRandom(Integer randomCount);

    List<Documentation> findDocByCommentDesc(Integer limit);

    List<Documentation> findDocByRecommend(Integer limit);
}
