import com.sylg.blog.service.documentation.ServiceDocumentationApplication;
import com.sylg.blog.service.documentation.cache.ViewsCacheStore;
import com.sylg.blog.service.documentation.common.dto.TemplateContext;
import com.sylg.blog.service.documentation.controller.web.BlogController;
import com.sylg.blog.service.documentation.domain.BlogUser;
import com.sylg.blog.service.documentation.domain.Documentation;
import com.sylg.blog.service.documentation.factory.TemplateContextFactory;
import com.sylg.blog.service.documentation.mapper.AnnouncementMapper;
import com.sylg.blog.service.documentation.mapper.BlogReviewMapper;
import com.sylg.blog.service.documentation.mapper.BlogUserMapper;
import com.sylg.blog.service.documentation.repository.DocRepository;
import com.sylg.blog.service.documentation.service.BlogUserService;
import com.sylg.blog.service.documentation.service.MailService;
import com.sylg.blog.service.documentation.service.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceDocumentationApplication.class)
public class insertTest {
    @Autowired
    private BlogUserService blogUserService;

    @Autowired
    private TagService tagService;

    @Autowired
    private DocRepository docRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateContextFactory templateContextFactory;

    @Autowired
    private BlogUserMapper blogUserMapper;

    @Autowired
    private ViewsCacheStore viewsCacheStore;

    @Autowired
    private BlogController blogController;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Autowired
    private BlogReviewMapper blogReviewMapper;

    @Test
    public void testBlogReview(){
        System.out.println(blogReviewMapper.findAllByIsPublishFalse());
    }

    @Test
    public void testTagService(){
        tagService.delete("5ea161595424d68759bdead9");
        //Update update = new Update();
        //Documentation documentation = new Documentation();
        //documentation.setId("5e8d89bcd630012df022ead8");
        //documentation.setUsername("pangqiyuan");
        //Tag tag = new Tag();
        //tag.setName("aaa");
        //documentation.setTags(Collections.singletonList(tag));
        //documentation.setIsComment(false);
        //BlogUtils.createUpdate(update, documentation);
        //String[] tags ={"a","b","c"};
        //System.out.println(Arrays.stream(tags).map(s -> {
        //    Tag tag = new Tag();
        //    tag.setName(s);
        //    return tag;
        //}).collect(Collectors.toList()));
        // List<Tag> tags = new ArrayList<>();
       // Tag tag1 = new Tag();
       // tag1.setName("a");
       // Tag tag2 = new Tag();
       // tag2.setName("b");
       // Tag tag3 = new Tag();
       // tag3.setName("c");
       // tags.add(tag1);
       // tags.add(tag2);
       // tags.add(tag3);
       // StringBuilder builder = new StringBuilder();
       //tags.forEach(tag -> builder.append(tag.getName()).append(","));
       // System.out.println(builder.toString());
        //System.out.println(tagService.findBlogIdByMainTag("JAVA后端"));
        //tagService.updateBlogId("5e9bfd23d6300156644423dd","5e8d89bcd630012df022ead8");
        //Tag tag = new Tag();
        //tag.setName("JAVA后端");
        //tag.setBlogNumber(1);
        //tagService.insertTag(tag);
    }

    @Test
    public void testAnnouncementMapper(){
        System.out.println(announcementMapper.selectAllByAnnouncementTimeDesc(5));

    }
    @Test
    public void testDesc(){
        List<String> list = Arrays.asList("5e8d89bcd630012df022ead8", "5ea16413d630015b6084945f","5e9881c777e85e0006348621");
        Sort sort = new Sort(Sort.Direction.DESC, "views");
        //PageRequest of = PageRequest.of(0, 8, sort);
        //System.out.println(docRepository.findAllByIdAndIsPublishTrue(list, of).getContent());
        Query query = new Query(new Criteria("_id").in(list).and("isPublish").is(true));
        System.out.println(mongoTemplate.find(query.with(sort).limit(1), Documentation.class));
        //System.out.println(docRepository.findAllByIdAndIsPublishTrue(list));
        //List<Documentation> documentations = (List<Documentation>) docRepository.findAllById(list);
        //System.out.println(documentations);


        //AggregationOperation aggregationOperation = new CountOperation("commentByBeans");
        //SortByCountOperation sortByCountOperation = new SortByCountOperation(aggregationOperation);
        //TypedAggregation<Documentation> typedAggr = Aggregation.newAggregation(Documentation.class,
        //        Aggregation.project("commentByBeans"),
        //        Aggregation.unwind("commentByBeans"),
        //        Aggregation.sort(Sort.Direction.DESC,"commentByBeans.commentTime"));
        //AggregationResults<Documentation> aggregate = mongoTemplate.aggregate(typedAggr, Documentation.class);
        //System.out.println(aggregate.getMappedResults());
        //Pattern pattern = BlogUtils.getPattern("y");
        //Sort sort = new Sort(Sort.Direction.DESC, "score");
        //Query query = new Query(Criteria.where("docName").regex(pattern));
        //List<Documentation> documentations = mongoTemplate.find(query.with(sort), Documentation.class);
        //System.out.println(documentations);
        //System.out.println(LocalDateTime.parse("2020-04-08 04:22:20", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));\
      //  0.05006637082872328
      //  0.04270848328864561
      //  0.006558134586831564
      //  0.005965557410348954
      //  0.031283999004469605
      //  0.722822346724572
      //  0.03918039233483045
      //  0.006127055049208141
      //  0.13832661952659725
      //3  System.out.println(BlogUtils.blogScore(140, "2020-04-15 04:22:20"));
      //4  System.out.println(BlogUtils.blogScore(121, "2020-04-15 04:22:20"));
      // 8 System.out.println(BlogUtils.blogScore(172, "2020-04-01 04:22:20"));
      // 9 System.out.println(BlogUtils.blogScore(120, "2020-04-05 04:22:20"));
      // 6 System.out.println(BlogUtils.blogScore(1500, "2020-03-16 04:22:20"));
      //1  System.out.println(BlogUtils.blogScore(701, "2020-04-16 04:22:20"));
      //5  System.out.println(BlogUtils.blogScore(413, "2020-04-10 04:22:20"));
      // 7 System.out.println(BlogUtils.blogScore(228, "2020-03-25 04:22:20"));
      // System.out.println(BlogUtils.blogScore(0, "2020-04-16 04:22:20"));
        //List<Documentation> documentations = docRepository.findDocumentationsOrderByViewsDesc();
        //System.out.println(documentations);
        //System.out.println(docRepository.findAll());
        //Sort sort = new Sort(Sort.Direction.DESC, "views").and(new Sort(Sort.Direction.DESC, "createTime"));
        //Query query = new Query();
        //System.out.println(mongoTemplate.find(query.with(sort), Documentation.class));

        //Sort sort = new Sort(Sort.Direction.DESC, "score");
        //Query query = new Query();
        //System.out.println(mongoTemplate.find(query.with(sort).limit(2), Documentation.class));

    }

    @Test
    public void testAop(){
        Query query = new Query(Criteria.where("_id").is("5e8d89bcd630012df022ead8").and("commentByBeans.cid").is("1"));
        Update update = new Update();
        Documentation.CommentByBean commentByBean = new Documentation.CommentByBean();
        //Documentation.CommentByBean commentByBean1 = new Documentation.CommentByBean();
        //commentByBean1.setCid("2");
        commentByBean.setCid("3");
        //commentByBean.setReplyComment(Collections.singletonList(commentByBean1));
        update.addToSet("commentByBeans.$.replyComment",commentByBean);
        mongoTemplate.upsert(query, update, Documentation.class);
//        DBObject obj = new BasicDBObject();
//
//        obj.put("_id", "5e8d89bcd630012df022ead8");
//        obj.put("commentByBeans.cid","454b8d87e3cb4d9986b3703f1f760190");
//
//        BasicDBObject fieldsObject = new BasicDBObject();
//
//        fieldsObject.put("commentByBeans.", true);
//
//
//        Query query = new BasicQuery( obj.toString(), fieldsObject.toString());
//// 注意这里泛型的选择
//        List<Documentation> documentations = mongoTemplate.find(query, Documentation.class);
//        Optional<List<String>> list = documentations.stream().map(documentation -> documentation.getCommentByBeans().stream().map(Documentation.CommentByBean::getCid).collect(Collectors.toList())).findAny();
//        if (list.isPresent()) {
//            List<String> strings = list.get();
//            System.out.println(strings);
//        }
        //de4cd3dfd7b24d3785091deb97a17124
        //String s = BlogUtils.randomUUIDWithoutDash();
        //System.out.println(s);
        //System.out.println(UUID.randomUUID().toString());
        //blogController.dashboard("5e8d89bcd630012df022ead8");
        //List<Documentation> documentations = mongoTemplate.findAll(Documentation.class);
        //System.out.println(documentations);
        //Tag tag = new Tag();
        //tag.setName("java");
        //Tag tag1 = new Tag();
        //tag1.setName("基础");
        //Documentation.CommentByBean commentByBean = new Documentation.CommentByBean("pang", "aaa", "bbb");
        ////List<Tag> list = Arrays.asList(tag, tag1);
        //Query query = new Query(Criteria.where("_id").is("5e8d89bcd630012df022ead8"));
        //Update update = new Update();
        //update.addToSet("commentByBeans",commentByBean);
        //mongoTemplate.upsert(query, update, Documentation.class);
    }

    @Test
    public void testViewsCahceStore(){
        viewsCacheStore.syncDataToDatabase("5e8d89bcd630012df022ead8",10);
        Optional<Integer> integer = viewsCacheStore.get("5e8d89bcd630012df022ead8", true);

    }

    @Test
    public void testLocalDataTime(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
    }

    @Test
    public void testBlogSelect(){
        Optional<Documentation> repository = docRepository.findById("5e8d89bcd630012df022ead");
        System.out.println(repository.orElse(null));
    }
    @Test
    public void testPwd(){
        BlogUser blogUser = blogUserMapper.selectPwdQuestionsByLoginCode("1102552196");
        BlogUser blogUser1 = new BlogUser();
        blogUser1.setPwdQuestionAnswer("庞志强");
        blogUser1.setPwdQuestionAnswer2("陈艳红");
        blogUser1.setPwdQuestionAnswer3("庞淇元");
        System.out.println(blogUser);
        System.out.println(blogUser1);
        System.out.println(blogUser.equals(blogUser1));
    }

    @Test
    public void testMailSend() throws MessagingException {
        TemplateContext templateContext = new TemplateContext();
        templateContext.setUsername("庞淇元");
        String process = templateContextFactory.process(templateContext);
        mailService.sendHtmlMail("hwpqy9862@163.com","测试邮件",process);
    }

    @Test
    public void update(){
        BlogUser blogUser = new BlogUser();
        blogUser.setLoginCode("1102552196");
        blogUser.setEmail("1102552196@qq.com");
        int i = blogUserService.updateEmailByLoginCode(blogUser);
    }

    @Test
    public void insert(){
        BlogUser blogUser = new BlogUser();
        blogUser.setLoginCode("1102552196");
        blogUser.setUserName("pangqiyuan");
        blogUser.setPassword("123456");
        blogUser.setMgrType("1");
        blogUserService.insert(blogUser);
    }

    @Test
    public void insertDoc(){
        Documentation documentation = new Documentation();
        documentation.setUsername("pangqiyuan");
        documentation.setDocName("java");
        documentation.setContent("java牛逼");
        docRepository.insert(documentation);
    }

    @Test
    public void time(){
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
    }


    @Test
    public void save(){
        Documentation documentation = docRepository.findDocumentationByDocNameAndUsername("java优秀", "pangqiyuan");
        System.out.println(documentation);
    }

    //@Test
    //public void createContext(){
    //    TemplateContext templateContext = new TemplateContext();
    //    templateContext.setUsernmae("aaa");
    //    templateContext.setUrl("bbb");
    //    TemplateContextFactory.createContext(templateContext);
    //}




}
