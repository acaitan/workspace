package com.shu.edu.tac.toutiao.dao;

import com.shu.edu.tac.toutiao.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository(value = "UserDao")
@Mapper
public interface UserDao {
    String TABLE_NAME = "user";
    String SELECT_FIELD = "id,name,password,salt,head_url";
    String INSERT_FIELD = "name,password,salt,head_url";

    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELD,
            ") values(#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select",SELECT_FIELD,"from",TABLE_NAME,"where id = #{id}"})
    User selectById(Long id);

    @Update({"update",TABLE_NAME,"set password = 'tac1' where id = #{id}"})
    void updateByPass(Long id);

    @Delete({"delete from",TABLE_NAME,"where id=#{id}"})
    void deleteById(Long id);
}
