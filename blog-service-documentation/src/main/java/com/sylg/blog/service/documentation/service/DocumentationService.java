package com.sylg.blog.service.documentation.service;

import com.sylg.blog.service.documentation.domain.Documentation;

import java.util.List;

public interface DocumentationService {

    List<Documentation> findAllByName(String username);

    void insertDoc(Documentation documentation);

    List<Documentation> findAll();

    Documentation findDocByDocName(String docName,String username);

    void saveDocByDocName(Documentation documentation);

    void deleteDocByDocName(String docId);
}
