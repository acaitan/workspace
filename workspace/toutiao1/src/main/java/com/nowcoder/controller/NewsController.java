package com.nowcoder.controller;

import com.nowcoder.model.HostHolder;
import com.nowcoder.model.News;
import com.nowcoder.service.NewsService;
import com.nowcoder.service.QiniuService;
import com.nowcoder.service.UserService;
import com.nowcoder.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;


@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Resource
    private NewsService newsService;
    @Resource
    private QiniuService qiniuService;
    @Resource
    private HostHolder hostHolder;
    @Resource
    private UserService userService;

    @RequestMapping(value = "/user/addNews/")
    @ResponseBody
    public String addNews(@RequestParam(value = "image")String image,
                          @RequestParam(value = "title")String title,
                          @RequestParam(value = "link")String link){
        try {
            News news = new News();
            news.setImage(image);
            news.setLink(link);
            news.setTitle(title);
            news.setCreatedDate(new Date());
            if (hostHolder.getUser() !=null){
                news.setUserId(hostHolder.getUser().getId());
            }else {
                news.setUserId(123);
            }
            newsService.addNews(news);
            return ToutiaoUtil.getJSONString(0);

        }catch (Exception e){
            logger.error("添加失败"+e.getMessage());
            return ToutiaoUtil.getJSONString(1,"添加失败");
        }

        //return null;
    }

    @RequestMapping(value = "/image")
    @ResponseBody
    public void image(@RequestParam("name")String imageName,
                      HttpServletResponse response){
        response.setContentType("image/jpeg");
        try {
            StreamUtils.copy(new FileInputStream(new File(ToutiaoUtil.IMAGE_DIR+imageName)),
                    response.getOutputStream());
        } catch (IOException e) {
            logger.error("读取图片错误"+e.getMessage());
        }

    }

    @RequestMapping(value = "/uplodeImage")
    @ResponseBody
    public String uplodeImage(@RequestParam("file")MultipartFile file){
        try {
            String fileUrl = newsService.saveImage(file);
            //String fileUrl = qiniuService.saveImage(file);
            if (fileUrl == null){
                return ToutiaoUtil.getJSONString(1,"上传图片失败");
            }
            return ToutiaoUtil.getJSONString(0,fileUrl);

        }catch (Exception e){

            logger.error("上传失败"+e.getMessage());
            return ToutiaoUtil.getJSONString(1,"上传失败");
        }

    }

    @RequestMapping(value = "/news/{newsId}",method = {RequestMethod.GET})
    public String comment(@PathVariable(value = "newsId")Long newsId, Model model){
        News news = newsService.getNews(newsId);

        if (null!=news){
            model.addAttribute("news",news);
            model.addAttribute("owner",userService.getUser(news.getUserId()));
        }

        return "detail";
    }



}
