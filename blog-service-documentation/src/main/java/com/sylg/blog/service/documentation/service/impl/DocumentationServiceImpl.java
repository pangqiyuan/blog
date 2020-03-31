package com.sylg.blog.service.documentation.service.impl;

import com.sylg.blog.service.documentation.domain.Documentation;
import com.sylg.blog.service.documentation.repository.DocRepository;
import com.sylg.blog.service.documentation.service.DocumentationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author pangqiyuan
 */
@Service
public class DocumentationServiceImpl implements DocumentationService {

    @Resource
    private DocRepository docRepository;


    @Override
    public List<Documentation> findAllByName(String username) {
        return docRepository.findAllByUsername(username);
    }

    @Override
    public void deleteDocByDocName(String docId) {
        docRepository.deleteById(docId);
    }

    @Override
    public void insertDoc(Documentation documentation){
        documentation.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        docRepository.insert(documentation);
    }

    @Override
    public List<Documentation> findAll() {
        return docRepository.findAll();
    }

    @Override
    public Documentation findDocByDocName(String docName,String username) {
        return docRepository.findDocumentationByDocNameAndUsername(docName,username);
    }

    @Override
    public void saveDocByDocName(Documentation documentation){
        documentation.setModifyTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        docRepository.save(documentation);
    }
}
