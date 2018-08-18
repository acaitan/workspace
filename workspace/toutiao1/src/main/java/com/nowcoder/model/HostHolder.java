package com.nowcoder.model;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clera(){
        users.remove();
    }
}
