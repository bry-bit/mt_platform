package com.mt.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("index")
    public String Index(){
        return "index";
    }


    @RequestMapping("page")
    public String Page(String url) {
        System.out.println("路径 : "+url);
        return url;
    }

}
