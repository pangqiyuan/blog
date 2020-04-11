import com.sylg.blog.service.documentation.ServiceDocumentationApplication;
import com.sylg.blog.service.documentation.cache.ViewsCacheStore;
import com.sylg.blog.service.documentation.common.dto.TemplateContext;
import com.sylg.blog.service.documentation.Factory.TemplateContextFactory;
import com.sylg.blog.service.documentation.controller.web.BlogController;
import com.sylg.blog.service.documentation.domain.BlogUser;
import com.sylg.blog.service.documentation.domain.Documentation;
import com.sylg.blog.service.documentation.mapper.BlogUserMapper;
import com.sylg.blog.service.documentation.repository.DocRepository;
import com.sylg.blog.service.documentation.service.BlogUserService;
import com.sylg.blog.service.documentation.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceDocumentationApplication.class)
public class insertTest {
    @Autowired
    private BlogUserService blogUserService;

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

    @Test
    public void testAop(){
        //blogController.dashboard("5e8d89bcd630012df022ead8");
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
