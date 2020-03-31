import com.sylg.blog.service.documentation.ServiceDocumentationApplication;
import com.sylg.blog.service.documentation.common.dto.TemplateContext;
import com.sylg.blog.service.documentation.common.utils.TemplateContextFactory;
import com.sylg.blog.service.documentation.domain.BlogUser;
import com.sylg.blog.service.documentation.domain.Documentation;
import com.sylg.blog.service.documentation.repository.DocRepository;
import com.sylg.blog.service.documentation.service.BlogUserService;
import org.apache.commons.lang3.concurrent.Computable;
import org.apache.commons.lang3.concurrent.Memoizer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceDocumentationApplication.class)
public class insertTest {
    @Autowired
    private BlogUserService blogUserService;

    @Autowired
    private DocRepository docRepository;

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
