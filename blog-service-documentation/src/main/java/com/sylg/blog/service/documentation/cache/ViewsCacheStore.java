package com.sylg.blog.service.documentation.cache;


import com.sylg.blog.service.documentation.domain.Documentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

/**
 * @program: blog
 * @description: blog views cache
 * @author: 忆地球往事
 * @create: 2020-04-10 19:36
 **/
@Component
public class ViewsCacheStore extends InMemoryCacheStore {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void syncDataToDatabase(String key, Integer value) {
        Query query = Query.query(Criteria.where("_id").is(key));
        Update update = new Update();
        update.set("views",value);
        mongoTemplate.updateFirst(query,update, Documentation.class);
    }

    public Integer getDataFromDatabase(String id){

        Documentation document = mongoTemplate.findById(id, Documentation.class);

        assert document != null;
        return document.getViews();
    }


}

