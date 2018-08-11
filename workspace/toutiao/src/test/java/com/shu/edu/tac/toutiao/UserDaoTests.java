package com.shu.edu.tac.toutiao;

import com.shu.edu.tac.toutiao.dao.NewsDao;
import com.shu.edu.tac.toutiao.dao.UserDao;
import com.shu.edu.tac.toutiao.model.News;
import com.shu.edu.tac.toutiao.model.User;
import com.shu.edu.tac.toutiao.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
//@Sql("/init-schema.sql")
public class UserDaoTests {
	@Resource
	//@Autowired
	private UserDao userDao;

	@Resource
	private UserService userService;

	@Resource
    private NewsDao newsDao;

	@Test
	public void contextLoads() {
		Random r = new Random();
        News news = new News();
		for(int i = 0;i<15;i++){
			/*User user = new User();
			user.setName(String.format("USER%d",i*15));
			user.setHeadUrl(String.format("hettp://images.tsc.edu.shu.com/head/%dt.png",r.nextInt(1000)));
			user.setPassword("tac");
			user.setSalt("lxb");
			userDao.addUser(user);*/


			news.setTitle(String.format("nowcoder%d",i));
			news.setUserId(i+1L);
			news.setCommentCount(i+0L);
            Date date = new Date();
            date.setTime(date.getTime()+1000*3600*5*i);
            news.setCreateDate(date);
            news.setImage(String.format("http://images.tac.edu.shu.com/head/%dm.png",r.nextInt(1000)));
            news.setLikeCount(i+1L);
            news.setLink(String.format("http://www.nowcoder.com/link/{%d}.html",i));

            newsDao.addNews(news);




		}

		/*userDao.selectById(1L);
		userDao.updateByPass(3L);
		Assert.assertEquals("tac1",userDao.selectById(3L).getPassword());
		userDao.deleteById(1L);
		Assert.assertNull(userDao.selectById(1L));*/

		Long id = 2L;
		User user = userService.selectByUserId(id);
		Assert.assertEquals("tac",user.getPassword());

	}

}
