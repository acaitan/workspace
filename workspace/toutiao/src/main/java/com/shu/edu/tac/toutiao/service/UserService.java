package com.shu.edu.tac.toutiao.service;

import com.shu.edu.tac.toutiao.dao.UserDao;
import com.shu.edu.tac.toutiao.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource(name = "UserDao")
    private UserDao userDao;

    public User selectByUserId(Long id){

        return userDao.selectById(id);
    }

}
