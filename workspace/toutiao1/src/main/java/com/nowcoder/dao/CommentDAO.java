package com.nowcoder.dao;

import com.nowcoder.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface CommentDAO {

    String TABLE_NAME = "comment";
    String INSET_FIELDS = " user_id, entity_id, entity_type, content ,created_date,status";
    String SELECT_FIELDS = " id, " + INSET_FIELDS;

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where entity_id=#{entityId} and entity_type = #{entityType} order by id desc"})
    List<Comment> selectByEntity(@Param("entityId")Long entityId, @Param("entityType")Long entityType);

    @Insert({"insert into",TABLE_NAME,"(",INSET_FIELDS,") values(#{userId},#{entityId},#{entityType},#{content},#{createdDate},#{status})"})
    int insertComment(Comment comment);

    @Select({"select count(id) from",TABLE_NAME,"where entity_id=#{entityId} and entity_type = #{entityType}"})
    int comentCount(@Param("entityId")Long entityId, @Param("entityType")Long entityType);

}

