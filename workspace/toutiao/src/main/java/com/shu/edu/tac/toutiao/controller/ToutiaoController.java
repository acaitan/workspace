package com.shu.edu.tac.toutiao.controller;

import com.shu.edu.tac.toutiao.model.News;
import com.shu.edu.tac.toutiao.service.NewsService;
import com.shu.edu.tac.toutiao.service.ToutiaoService;
import com.shu.edu.tac.toutiao.model.User;
import com.shu.edu.tac.toutiao.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ToutiaoController {

    @Resource
    private ToutiaoService toutiaoService;
    @Resource
    private UserService userService;
    @Resource
    private NewsService newsService;

    @RequestMapping("/")
    @ResponseBody
    public String print(HttpSession session) {

        return "Hello World:"+session.getAttribute("msg")+"<br/>"+toutiaoService.say();
    }

    @RequestMapping("/profile/{groupid}/{name}")
    @ResponseBody
    public String profile(@PathVariable(value = "groupid") String groupid,
                          @PathVariable("name") String name,
                          @RequestParam(value = "type",defaultValue = "1") int type,
                          @RequestParam(value = "key",defaultValue = "nowcoder")String key){

        return String.format("GID{%s},NAME{%s},TYPE{%d},KEY{%s}",groupid,name,type,key);
    }

    @RequestMapping(value = {"/vm"})
    public String news(Model model){
        model.addAttribute("value1","vv1");


        List<String> colors = Arrays.asList(new String[]{"RED","GREEN","BLUE"});

        Map<String,String> map = new HashMap<>();
        for(int i = 0;i<4;i++){
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("colors",colors);
        model.addAttribute("map",map);

        model.addAttribute("user",new User());

        return "news";
    }

    @RequestMapping(value = {"/request"})
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session){
        StringBuffer sb = new StringBuffer();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br/>");
        }


        sb.append(request.getCookies()+"<br/>");

        for(Cookie cookie : request.getCookies()){
            sb.append(cookie.getName()+":"+cookie.getValue()+"<br/>");
        }
        return sb.toString();
    }

    @RequestMapping(value = {"/response"})
    @ResponseBody
    public String response(@CookieValue(value = "nowcoderid",defaultValue = "aa" ) String nowcoderid,
                           @RequestParam(value = "value",defaultValue = "value")String value,
                           @RequestParam(value = "key",defaultValue = "key")String key,
                           HttpServletResponse response){
        StringBuffer sb = new StringBuffer();
        sb.append("Status:"+response.getStatus()+"<br/>");
        sb.append("HeaderNames:"+response.getHeaderNames()+"<br/>");
        sb.append("CharacterEncoding:"+response.getCharacterEncoding()+"<br/>");
        sb.append("Locale:"+response.getLocale()+"<br/>");
        sb.append("ContentType:"+response.getContentType()+"<br/>");
        //return sb.toString();


        response.addCookie(new Cookie(key,value));
        response.addHeader(key,value);

        return "nowcoder:"+nowcoderid;

    }

    @RequestMapping("/redirect/{code}")
    public String redic(@PathVariable("code") int code,
                              HttpSession session){
        /*RedirectView redirectView = new RedirectView("/",true);
        if(301 == code){
            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return redirectView;*/

        session.setAttribute("msg","nihao ");
        return "redirect:/";

    }


    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(value = "key")String key){
        if ("admin".equals(key)){
            return "helloWorld";
        }
        throw new IllegalArgumentException("kew 错误");
    }

    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){
        return "error:"+e.getMessage();
    }

    @RequestMapping("/users/{id}")
    @ResponseBody
    public User user(@PathVariable("id") Long id){
        return userService.selectByUserId(id);

    }

    @RequestMapping("/news/profile/{id}/{offset}/{limit}")
    @ResponseBody
    public List<News> news(@PathVariable("id") Long id,
                           @PathVariable("offset")int offset,
                           @PathVariable("limit")int limit){
        return newsService.selectByNewsId(id,offset,limit);

    }
}
