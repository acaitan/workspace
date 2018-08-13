package com.shu.edu.tac.toutiao.controller;

import com.shu.edu.tac.toutiao.model.News;
import com.shu.edu.tac.toutiao.model.ViewObject;
import com.shu.edu.tac.toutiao.service.NewsService;
import com.shu.edu.tac.toutiao.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Controller

public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Resource
    private NewsService newsService;

    @Resource
    private UserService userService;

    @RequestMapping(value = {"/news"},method = {RequestMethod.GET})
    public String news(Model model){

        List<ViewObject> vos = getNews(0L,0,10);
        model.addAttribute("vos",vos);

        return "home";
    }

    @RequestMapping(value = {"/user/{userId}/"},method = {RequestMethod.GET})
    public String user(@PathVariable(value = "userId") Long userId,
                       Model model){

        List<ViewObject> vos = getNews(userId,0,10);
        model.addAttribute("vos",vos);

        return "home";
    }

    public List<ViewObject> getNews(@PathVariable(value = "userId") Long userId,
                   @PathVariable(value = "offset") int offset,
                   @PathVariable(value = "limit")int limit){
        if(null==userId){
            return null;
        }
        List<ViewObject> vos = new ArrayList<>();

        List<News> news = newsService.selectByNewsId(userId,offset,limit);
        for(News news1 :news){
            ViewObject vo = new ViewObject();
            vo.set("news",news1);
            vo.set("user",userService.selectByUserId(news1.getUserId()));
            vos.add(vo);
        }

        return vos;
    }
}
