package com.shu.edu.tac.toutiao;

import com.shu.edu.tac.toutiao.dao.NewsDao;
import com.shu.edu.tac.toutiao.model.News;
import com.shu.edu.tac.toutiao.model.User;
import com.shu.edu.tac.toutiao.service.NewsService;
import com.shu.edu.tac.toutiao.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)

public class UserServiceTests {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTests.class);
    @Resource
    private UserService userService;

    @Resource
    private NewsService newsService;

    @Resource
    private NewsDao newsDao;

    @Test
    public void testUser() {
        Long id = 35L;
        User user = userService.selectByUserId(id);
        logger.info(user.getPassword());


    }

    @Test
    public void testNews() {
        List<News> news = newsDao.selectByWhereSql(1L,0,10);

        logger.info("newsSize",news.size());

        logger.info(news.get(0).getTitle());

       /* News news = newsService.selectById(1L);
        logger.info("title:"+news.getTitle());*/

    }

}
