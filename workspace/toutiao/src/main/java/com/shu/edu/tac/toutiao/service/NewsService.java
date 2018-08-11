package com.shu.edu.tac.toutiao.service;

import com.shu.edu.tac.toutiao.dao.NewsDao;
import com.shu.edu.tac.toutiao.model.News;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewsService {
    @Resource
    private NewsDao newsDao;

    public List<News> selectByNewsId(Long id,int offset,int limit){

        return newsDao.selectByWhereSql(id,offset,limit);
    }

    public News selectById(Long id){

        return newsDao.selectById(id);
    }
}
