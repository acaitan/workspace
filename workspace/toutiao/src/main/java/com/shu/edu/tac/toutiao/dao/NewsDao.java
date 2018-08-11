package com.shu.edu.tac.toutiao.dao;

import com.shu.edu.tac.toutiao.model.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("NewsDao")
@Mapper
public interface NewsDao {
    String TABLE_NAME = "news";
    String INSERT_FIELD = "title,link,like_count,comment_count,image,user_id,created_date";
    String SELECT_FIELD = "id,"+INSERT_FIELD;

    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELD,
            ") values(#{title},#{link},#{likeCount},#{commentCount},#{image},#{userId},#{createdDate})"})
    void addNews(News news);

    @Select({"select",SELECT_FIELD,"from",TABLE_NAME,"where id = #{id}"})
    News selectById(Long id);

    @Select({"select",SELECT_FIELD,"from",TABLE_NAME,"where user_id = 1"})
    List<News> selectByWhereSql(Long userId,int offset,int limit);
}
